package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.item.ItemInstance;
import com.nsoz.v2.domain.item.calculator.KenshikoEnchantEngine;
import com.nsoz.v2.domain.model.PlayerCharacter;
import com.nsoz.v2.domain.world.WorldManager;
import com.nsoz.v2.domain.world.Zone;
import com.nsoz.v2.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 3. InventoryPacketController: High-Cohesion controller for Bags, Equips, Boxes,
 * and Kenshiko Item Enhancements.
 */
public class InventoryPacketController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryPacketController.class);
    private static final InventoryPacketController INSTANCE = new InventoryPacketController();

    public static final byte CMD_ITEM_USE = 11;
    public static final byte CMD_ITEM_BODY_TO_BAG = 15;
    public static final byte CMD_UPGRADE = 21;
    public static final byte CMD_REQUEST_ITEM_INFO = 42;
    public static final byte CMD_SUB_COMMAND = -30;
    public static final byte CMD_PLAYER_LOAD_VUKHI = -117;

    private final ItemRepository itemRepository = ItemRepository.getInstance();

    private InventoryPacketController() {}

    public static InventoryPacketController getInstance() {
        return INSTANCE;
    }

    public void handlePacket(V2Session session, V2Message msg) {
        if (!session.isInGame() || session.getActiveCharacter() == null) {
            return;
        }

        byte cmd = msg.getCommand();
        try {
            switch (cmd) {
                case CMD_ITEM_USE:
                    handleItemUse(session, msg.reader());
                    break;
                case CMD_ITEM_BODY_TO_BAG:
                    handleItemBodyToBag(session, msg.reader());
                    break;
                case CMD_UPGRADE:
                    handleUpgrade(session, msg.reader());
                    break;
                case CMD_REQUEST_ITEM_INFO:
                    handleRequestItemInfo(session, msg.reader());
                    break;
                default:
                    logger.debug("Unhandled inventory opcode [{}] for character [{}]", cmd, session.getActiveCharacter().getName());
                    break;
            }
        } catch (IOException e) {
            logger.error("Error handling inventory packet [{}] for character [{}]", cmd, session.getActiveCharacter().getName(), e);
        }
    }

    /**
     * Handles CMD 11 (ITEM_USE): Equips weapons/equipment or consumes items.
     */
    private void handleItemUse(V2Session session, DataInputStream reader) throws IOException {
        PlayerCharacter character = session.getActiveCharacter();
        byte bagSlot = reader.readByte();

        ItemInstance item = character.getInventory().getBagItem(bagSlot);
        if (item == null) {
            logger.warn("Character [{}] tried to use empty bag slot [{}]", character.getName(), bagSlot);
            return;
        }

        logger.info("Character [{}] using item [{}] (templateId={}) from slot [{}]",
                character.getName(), item.getId(), item.getTemplateId(), bagSlot);

        if (item.isWeapon()) {
            int equipSlot = character.getInventory().equip(bagSlot);
            if (equipSlot >= 0) {
                ItemInstance equippedWeapon = character.getInventory().getEquipment(equipSlot);
                itemRepository.updateLocation(equippedWeapon.getId(), "EQUIP", (short) equipSlot);

                ItemInstance swappedBagItem = character.getInventory().getBagItem(bagSlot);
                if (swappedBagItem != null) {
                    itemRepository.updateLocation(swappedBagItem.getId(), "BAG", (short) bagSlot);
                }

                // Send CMD 11 response
                sendItemUseResponse(session, bagSlot, character);

                // Broadcast weapon graphic update to Zone
                broadcastWeaponChange(character);
            }
        }
    }

    /**
     * Handles CMD 15 (ITEM_BODY_TO_BAG): Unequips item from body back to bag.
     */
    private void handleItemBodyToBag(V2Session session, DataInputStream reader) throws IOException {
        PlayerCharacter character = session.getActiveCharacter();
        byte equipSlot = reader.readByte();

        ItemInstance item = character.getInventory().getEquipment(equipSlot);
        if (item == null) {
            logger.warn("Character [{}] tried to unequip empty equip slot [{}]", character.getName(), equipSlot);
            return;
        }

        int bagSlot = character.getInventory().unequip(equipSlot);
        if (bagSlot >= 0) {
            itemRepository.updateLocation(item.getId(), "BAG", (short) bagSlot);

            // Send CMD 15 response
            V2Message res = new V2Message(CMD_ITEM_BODY_TO_BAG);
            DataOutputStream dos = res.writer();
            dos.writeByte(character.getCombat().getSpeed());
            dos.writeInt(character.getState().getMaxHp());
            dos.writeInt(character.getState().getMaxMp());
            dos.writeShort(0); // eff5buffhp
            dos.writeShort(0); // eff5buffmp
            dos.writeByte(equipSlot);
            dos.writeByte(bagSlot);
            dos.writeShort(character.getHead());
            dos.flush();
            session.send(res);

            // Broadcast weapon graphic update to Zone
            broadcastWeaponChange(character);

            logger.info("Character [{}] unequipped item [{}] to bag slot [{}]",
                    character.getName(), item.getTemplateId(), bagSlot);
        } else {
            logger.warn("Character [{}] failed to unequip: bag is full", character.getName());
        }
    }

    /**
     * Handles CMD 21 (UPGRADE): Kenshiko enchanting system (+1 -> +16).
     */
    private void handleUpgrade(V2Session session, DataInputStream reader) throws IOException {
        PlayerCharacter character = session.getActiveCharacter();
        boolean isGold = reader.readBoolean();
        int bagSlot = reader.readUnsignedByte();

        ItemInstance item = character.getInventory().getBagItem(bagSlot);
        if (item == null) {
            logger.warn("Character [{}] tried to upgrade empty bag slot [{}]", character.getName(), bagSlot);
            return;
        }

        // Enchantment cost: 1000 Yen per attempt
        long costYen = 1000;
        if (!character.getInventory().consumeYen(costYen)) {
            sendDialog(session, "Bạn không đủ Yên để thực hiện cường hóa!");
            return;
        }

        KenshikoEnchantEngine.UpgradeResult result = KenshikoEnchantEngine.attemptUpgrade(item.getUpgradeLevel());
        item.setUpgradeLevel(result.newLevel());
        itemRepository.updateUpgrade(item.getId(), item.getUpgradeLevel());

        byte resultType = (byte) (result.isSuccess() ? 1 : 0);

        V2Message res = new V2Message(CMD_UPGRADE);
        DataOutputStream dos = res.writer();
        dos.writeByte(resultType);
        dos.writeInt(session.getUserAccount() != null ? session.getUserAccount().getLuong() : 0);
        dos.writeInt((int) character.getInventory().getXu());
        dos.writeInt((int) character.getInventory().getYen());
        dos.writeByte(item.getUpgradeLevel());
        dos.flush();
        session.send(res);

        logger.info("Character [{}] upgraded item [{}] -> Level +{}, result={}",
                character.getName(), item.getTemplateId(), item.getUpgradeLevel(), result.message());
    }

    /**
     * Handles CMD 42 (REQUEST_ITEM_INFO): Item detail inspection.
     */
    private void handleRequestItemInfo(V2Session session, DataInputStream reader) throws IOException {
        PlayerCharacter character = session.getActiveCharacter();
        byte typeUI = reader.readByte();
        byte indexUI = reader.readByte();

        ItemInstance item = null;
        if (typeUI == 3) {
            // Bag
            item = character.getInventory().getBagItem(indexUI);
        } else if (typeUI == 4 || typeUI == 5) {
            // Equipment
            item = character.getInventory().getEquipment(indexUI);
        }

        if (item == null) return;

        V2Message res = new V2Message(CMD_REQUEST_ITEM_INFO);
        DataOutputStream dos = res.writer();
        dos.writeByte(typeUI);
        dos.writeByte(indexUI);
        dos.writeLong(item.getExpiresAt());
        dos.writeInt(item.getYenPrice());

        if (item.isWeapon()) {
            dos.writeByte(item.getSys());
            // Standardized Options:
            // ID 0: Tấn công
            dos.writeByte(0);
            dos.writeInt(item.getAttackBonus());
            // ID 31: Kháng chí mạng
            dos.writeByte(31);
            dos.writeInt(item.getTier() * 5);
            // ID 33: Miễn thương %
            dos.writeByte(33);
            dos.writeInt(item.getTier() * 2);
        }
        dos.flush();
        session.send(res);
    }

    private void sendItemUseResponse(V2Session session, byte bagSlot, PlayerCharacter character) throws IOException {
        V2Message res = new V2Message(CMD_ITEM_USE);
        DataOutputStream dos = res.writer();
        dos.writeByte(bagSlot);
        dos.writeByte(character.getCombat().getSpeed());
        dos.writeInt(character.getState().getMaxHp());
        dos.writeInt(character.getState().getMaxMp());
        dos.writeShort(0); // eff5buffhp
        dos.writeShort(0); // eff5buffmp
        dos.flush();
        session.send(res);
    }

    private void broadcastWeaponChange(PlayerCharacter character) {
        Zone zone = WorldManager.getInstance().findZone(
                character.getSpatial().getMapId(), character.getSpatial().getZoneId());
        if (zone == null) return;

        ItemInstance weapon = character.getInventory().getEquippedWeapon();
        short weaponTemplateId = (short) (weapon != null ? weapon.getTemplateId() : -1);

        try {
            V2Message msg = new V2Message(CMD_SUB_COMMAND);
            DataOutputStream dos = msg.writer();
            dos.writeByte(CMD_PLAYER_LOAD_VUKHI);
            dos.writeInt(character.getId());
            dos.writeInt(character.getState().getHp());
            dos.writeInt(character.getState().getMaxHp());
            dos.writeShort(0);
            dos.writeShort(0);
            dos.writeShort(weaponTemplateId);
            dos.flush();
            zone.broadcast(msg, -1);
        } catch (IOException ignored) {}
    }

    private void sendDialog(V2Session session, String text) {
        AuthPacketController.getInstance().sendServerDialog(session, text);
    }
}
