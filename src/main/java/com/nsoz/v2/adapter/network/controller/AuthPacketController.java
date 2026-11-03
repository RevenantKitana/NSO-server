package com.nsoz.v2.adapter.network.controller;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.model.PlayerCharacter;
import com.nsoz.v2.domain.model.UserAccount;
import com.nsoz.v2.domain.service.AuthService;
import com.nsoz.v2.infrastructure.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

/**
 * 1. AuthPacketController: High-Cohesion controller handling Handshake, Client Meta,
 * Authentication, Character Listing, and Character Selection.
 */
public class AuthPacketController {
    private static final Logger logger = LoggerFactory.getLogger(AuthPacketController.class);
    private static final AuthPacketController INSTANCE = new AuthPacketController();

    public static final byte CMD_GET_SESSION_ID = -27;
    public static final byte CMD_SERVER_DIALOG = -26;
    public static final byte CMD_NOT_MAP = -28;
    public static final byte CMD_NOT_LOGIN = -29;
    public static final byte CMD_SUB_COMMAND = -30;
    public static final byte CMD_ME_LOAD_ALL = -127;
    public static final byte CMD_ME_LOAD_ACTIVE = -106;
    public static final byte CMD_OPEN_UI_BOX = 31;
    public static final byte CMD_BAG_SORT = -107;
    public static final byte CMD_REQUEST_MAPTEMPLATE = -109;
    public static final byte CMD_LOGIN = -127;
    public static final byte CMD_SELECT_PLAYER = -126;
    public static final byte CMD_CLIENT_INFO = -125;
    public static final byte CMD_CLIENT_OK = -101;
    public static final byte CMD_LOAD_RMS = -65;
    public static final byte CMD_SAVE_RMS = -67;

    private final AuthService authService = AuthService.getInstance();

    private AuthPacketController() {}

    public static AuthPacketController getInstance() {
        return INSTANCE;
    }

    /**
     * Sends initial cryptographic XOR handshake to client (CMD -27).
     */
    public void sendHandshake(V2Session session) {
        if (session.isSendKeyComplete()) {
            return;
        }

        session.generateKey(ServerConfig.getInstance().getServerId());
        byte[] key = session.getKey();

        try {
            V2Message ms = new V2Message(CMD_GET_SESSION_ID);
            DataOutputStream ds = ms.writer();
            ds.writeByte(key.length);
            ds.writeByte(key[0]);
            for (int i = 1; i < key.length; i++) {
                ds.writeByte(key[i] ^ key[i - 1]);
            }
            ds.flush();
            session.send(ms);
            session.setSendKeyComplete(true);
            logger.info("Sent handshake CMD -27 with key (length={}) to session [{}]", key.length, session.getSessionId());
        } catch (IOException e) {
            logger.error("Error sending handshake to session [{}]", session.getSessionId(), e);
        }
    }

    /**
     * Handles CMD.NOT_LOGIN (-29) packets: Client metadata and Login requests.
     */
    public void handleNotLogin(V2Session session, V2Message msg) {
        DataInputStream reader = msg.reader();
        try {
            byte subCommand = reader.readByte();
            switch (subCommand) {
                case CMD_CLIENT_INFO:
                    handleClientInfo(session, reader);
                    break;
                case CMD_LOGIN:
                    handleLogin(session, reader);
                    break;
                default:
                    logger.debug("Unhandled subCommand [{}] under CMD_NOT_LOGIN for session [{}]", subCommand, session.getSessionId());
                    break;
            }
        } catch (IOException e) {
            logger.error("Error processing CMD_NOT_LOGIN for session [{}]", session.getSessionId(), e);
        }
    }

    /**
     * Handles CMD.NOT_MAP (-28) packets: Character selection.
     */
    public void handleNotMap(V2Session session, V2Message msg) {
        DataInputStream reader = msg.reader();
        try {
            byte subCommand = reader.readByte();
            switch (subCommand) {
                case CMD_SELECT_PLAYER:
                    handleSelectPlayer(session, reader);
                    break;
                case CMD_REQUEST_MAPTEMPLATE:
                    handleRequestMapTemplate(session, reader);
                    break;
                case CMD_CLIENT_OK:
                    logger.info("Session [{}] sent CLIENT_OK (-101)", session.getSessionId());
                    break;
                default:
                    logger.info("Received unhandled subCommand [{}] under CMD_NOT_MAP for session [{}]", subCommand, session.getSessionId());
                    break;
            }
        } catch (IOException e) {
            logger.error("Error processing CMD_NOT_MAP for session [{}]", session.getSessionId(), e);
        }
    }

    private void handleRequestMapTemplate(V2Session session, DataInputStream reader) throws IOException {
        int mapId = reader.readUnsignedByte();
        logger.info("Session [{}] requested map template for mapId [{}]", session.getSessionId(), mapId);

        byte[] mapData = loadMapData(mapId);
        if (mapData != null) {
            V2Message ms = new V2Message(CMD_NOT_MAP);
            DataOutputStream ds = ms.writer();
            ds.writeByte(CMD_REQUEST_MAPTEMPLATE);
            ds.write(mapData);
            ds.flush();
            session.send(ms);
            logger.info("Sent map template [{}] ({} bytes) to session [{}]", mapId, mapData.length, session.getSessionId());
        } else {
            logger.error("Map data file not found for mapId [{}]", mapId);
        }
    }

    private byte[] loadMapData(int mapId) {
        try {
            File file = new File("Data/Map/" + mapId);
            if (!file.exists()) {
                file = new File("/home/ubuntu/nso-server/Data/Map/" + mapId);
            }
            if (file.exists()) {
                return Files.readAllBytes(file.toPath());
            }
        } catch (Exception e) {
            logger.error("Error reading map file for mapId [{}]", mapId, e);
        }
        return null;
    }

    private void handleClientInfo(V2Session session, DataInputStream reader) throws IOException {
        session.setClientType(reader.readByte());
        byte zoom = reader.readByte();
        session.setZoomLevel((zoom < 1 || zoom > 4) ? 1 : zoom);
        session.setGps(reader.readBoolean());
        session.setWidth(reader.readInt());
        session.setHeight(reader.readInt());
        session.setQwerty(reader.readBoolean());
        session.setTouch(reader.readBoolean());
        session.setPlatform(reader.readUTF());
        reader.readInt();  // unused
        reader.readByte(); // unused
        session.setLanguageId(reader.readByte());
        session.setProvider(reader.readInt());
        session.setAgent(reader.readUTF());

        logger.info("Session [{}] Client Info: platform={}, res={}x{}, zoom={}",
                session.getSessionId(), session.getPlatform(), session.getWidth(), session.getHeight(), session.getZoomLevel());
    }

    private void handleLogin(V2Session session, DataInputStream reader) throws IOException {
        String username = reader.readUTF().trim().toLowerCase();
        String password = reader.readUTF().trim();
        String version = reader.readUTF();
        session.setVersion(version);

        logger.info("Session [{}] attempting login for user [{}] (client ver: {})", session.getSessionId(), username, version);

        Optional<UserAccount> userOpt = authService.authenticate(username, password);
        if (!userOpt.isPresent()) {
            sendServerDialog(session, "Tài khoản hoặc mật khẩu không chính xác.");
            return;
        }

        UserAccount user = userOpt.get();
        session.setUserAccount(user);
        session.setAuthenticated(true);

        List<PlayerCharacter> characters = authService.getCharacters(user.getId());
        sendCharacterList(session, characters);
    }

    private void handleSelectPlayer(V2Session session, DataInputStream reader) throws IOException {
        if (!session.isAuthenticated()) {
            logger.warn("Unauthenticated session [{}] tried to select character", session.getSessionId());
            session.close();
            return;
        }

        String charName = reader.readUTF();
        int userId = session.getUserAccount().getId();
        List<PlayerCharacter> characters = authService.getCharacters(userId);

        Optional<PlayerCharacter> selected = characters.stream()
                .filter(c -> c.getName().equalsIgnoreCase(charName))
                .findFirst();

        if (!selected.isPresent()) {
            sendServerDialog(session, "Không tìm thấy thông tin nhân vật: " + charName);
            return;
        }

        PlayerCharacter character = selected.get();

        // Load Inventory Items from DB
        List<com.nsoz.v2.domain.item.ItemInstance> items =
                com.nsoz.v2.repository.ItemRepository.getInstance().findByCharacterId(character.getId());
        character.getInventory().loadItems(items);

        session.setActiveCharacter(character);
        session.setInGame(true);

        // Handshake packet sequence matching J2ME client & legacy server:
        sendDataBox(session);
        sendLoadAll(session);
        sendMeLoadActive(session);

        // Place Player in Map Zone via WorldManager
        com.nsoz.v2.domain.world.WorldManager.getInstance().joinZone(
                session, character.getSpatial().getMapId(), character.getSpatial().getZoneId());

        // Skill Shortcuts (LOAD_RMS -65) required by J2ME GameCanvas to avoid NPE
        sendSkillShortcut(session, "OSkill", new byte[]{-1, -1, -1, -1, -1}, (byte) 0);
        sendSkillShortcut(session, "KSkill", new byte[]{-1, -1, -1}, (byte) 0);
        sendSkillShortcut(session, "CSkill", new byte[]{-1, -1, -1, -1, -1}, (byte) 0);

        logger.info("Session [{}] successfully entered world as [{}] (Level {}, Map {}, Zone {})",
                session.getSessionId(), character.getName(), character.getState().getLevel(),
                character.getSpatial().getMapId(), character.getSpatial().getZoneId());
    }

    public void sendDataBox(V2Session session) {
        try {
            V2Message ms = new V2Message(CMD_OPEN_UI_BOX);
            DataOutputStream ds = ms.writer();
            ds.writeInt(0); // coin in box
            ds.writeByte(30); // numberCellBox
            for (int i = 0; i < 30; i++) {
                ds.writeShort(-1);
            }
            ds.flush();
            session.send(ms);
            logger.info("Sent OPEN_UI_BOX (31) to session [{}]", session.getSessionId());
        } catch (IOException e) {
            logger.error("Error sending OPEN_UI_BOX to session [{}]", session.getSessionId(), e);
        }
    }

    public void sendMeLoadActive(V2Session session) {
        try {
            V2Message ms = new V2Message(CMD_NOT_MAP);
            DataOutputStream ds = ms.writer();
            ds.writeByte(CMD_ME_LOAD_ACTIVE);
            ds.writeByte(0); // typeProtect = 0
            ds.flush();
            session.send(ms);
            logger.info("Sent ME_LOAD_ACTIVE (-28, -106) to session [{}]", session.getSessionId());
        } catch (IOException e) {
            logger.error("Error sending ME_LOAD_ACTIVE to session [{}]", session.getSessionId(), e);
        }
    }

    public void sendSkillShortcut(V2Session session, String key, byte[] data, byte type) {
        try {
            V2Message ms = new V2Message(CMD_SUB_COMMAND);
            DataOutputStream ds = ms.writer();
            ds.writeByte(CMD_LOAD_RMS);
            ds.writeUTF(key);
            ds.writeInt(data.length);
            ds.write(data);
            ds.writeByte(type);
            ds.flush();
            session.send(ms);
            logger.info("Sent skill shortcut [{}] to session [{}]", key, session.getSessionId());
        } catch (IOException e) {
            logger.error("Error sending skill shortcut [{}] to session [{}]", key, session.getSessionId(), e);
        }
    }

    /**
     * Handles CMD.SUB_COMMAND (-30) packets from client.
     */
    public void handleSubCommand(V2Session session, V2Message msg) {
        DataInputStream reader = msg.reader();
        try {
            byte subCommand = reader.readByte();
            switch (subCommand) {
                case CMD_LOAD_RMS:
                    handleLoadRms(session, reader);
                    break;
                case CMD_SAVE_RMS:
                    handleSaveRms(session, reader);
                    break;
                default:
                    logger.debug("Unhandled subCommand [{}] under CMD_SUB_COMMAND for session [{}]", subCommand, session.getSessionId());
                    break;
            }
        } catch (IOException e) {
            logger.error("Error processing CMD_SUB_COMMAND for session [{}]", session.getSessionId(), e);
        }
    }

    private void handleLoadRms(V2Session session, DataInputStream reader) throws IOException {
        String key = reader.readUTF();
        logger.info("Session [{}] requested LOAD_RMS for key [{}]", session.getSessionId(), key);
        byte[] data = new byte[0];
        if ("KSkill".equals(key)) {
            data = new byte[]{-1, -1, -1};
        } else if ("OSkill".equals(key)) {
            data = new byte[]{-1, -1, -1, -1, -1};
        } else if ("CSkill".equals(key)) {
            data = new byte[]{-1, -1, -1, -1, -1};
        }
        sendSkillShortcut(session, key, data, (byte) 0);
    }

    private void handleSaveRms(V2Session session, DataInputStream reader) throws IOException {
        String filename = reader.readUTF();
        int length = reader.readInt();
        byte[] data = new byte[length];
        reader.readFully(data);
        logger.info("Session [{}] saved RMS [{}] (length={})", session.getSessionId(), filename, length);
    }

    public void sendLoadAll(V2Session session) {
        PlayerCharacter c = session.getActiveCharacter();
        if (c == null) return;

        try {
            V2Message ms = new V2Message(CMD_SUB_COMMAND);
            DataOutputStream ds = ms.writer();
            ds.writeByte(CMD_ME_LOAD_ALL);
            ds.writeInt(c.getId());
            ds.writeUTF(""); // Clan name
            ds.writeByte(0); // Task ID
            ds.writeByte(c.getGender());
            ds.writeShort(c.getHead());
            ds.writeByte(c.getCombat().getSpeed());
            ds.writeUTF(c.getName());
            ds.writeByte(c.getCombat().getPk());
            ds.writeByte(0); // Type PK
            ds.writeInt(c.getState().getMaxHp());
            ds.writeInt(c.getState().getHp());
            ds.writeInt(c.getState().getMaxMp());
            ds.writeInt(c.getState().getMp());
            ds.writeLong(c.getState().getExp());
            ds.writeLong(0); // Exp down
            ds.writeShort(0); // Eff 5 buff HP
            ds.writeShort(0); // Eff 5 buff MP
            ds.writeByte(c.getCombat().getClazz());
            ds.writeShort(c.getState().getPotentialPoint());
            ds.writeShort(15); // Str
            ds.writeShort(5);  // Agi
            ds.writeInt(5);    // Vit
            ds.writeInt(5);    // Spi
            ds.writeShort(c.getState().getSkillPoint());
            ds.writeByte(0);   // Skills count
            ds.writeInt((int) c.getInventory().getXu());
            ds.writeInt((int) c.getInventory().getYen());
            ds.writeInt(session.getUserAccount() != null ? session.getUserAccount().getLuong() : 0);

            // Bag
            int bagSize = c.getInventory().getBagCapacity();
            ds.writeByte(bagSize);
            for (int i = 0; i < bagSize; i++) {
                com.nsoz.v2.domain.item.ItemInstance it = c.getInventory().getBagItem(i);
                if (it != null) {
                    ds.writeShort(it.getTemplateId());
                    ds.writeBoolean(it.isBound());
                    if (it.isWeapon()) {
                        ds.writeByte(it.getUpgradeLevel());
                    }
                    ds.writeBoolean(it.getExpiresAt() > 0);
                    ds.writeShort(it.getQuantity());
                } else {
                    ds.writeShort(-1); // Empty slot
                }
            }

            // Equipment (16 slots - wire format is 16 sequential slots without count byte)
            for (int i = 0; i < 16; i++) {
                com.nsoz.v2.domain.item.ItemInstance eq = c.getInventory().getEquipment(i);
                if (eq != null) {
                    ds.writeShort(eq.getTemplateId());
                    ds.writeByte(eq.getUpgradeLevel());
                    ds.writeByte(eq.getSys());
                } else {
                    ds.writeShort(-1);
                }
            }

            // Client trailer expected by cf.class (lines 2643-2733):
            ds.writeBoolean(true); // isHuman
            ds.writeBoolean(false); // isNhanBan
            ds.writeShort(c.getHead());
            short weaponId = (short) (c.getInventory().getEquippedWeapon() != null ? c.getInventory().getEquippedWeapon().getTemplateId() : -1);
            ds.writeShort(weaponId);
            ds.writeShort(-1); // body
            ds.writeShort(-1); // leg

            // thoiTrang (10 slots)
            for (int i = 0; i < 10; i++) {
                ds.writeShort(-1);
            }

            // fashion (16 slots)
            for (int i = 0; i < 16; i++) {
                ds.writeShort(-1);
            }

            ds.flush();
            session.send(ms);
            logger.info("Sent ME_LOAD_ALL (-127) to session [{}]", session.getSessionId());
        } catch (IOException e) {
            logger.error("Error sending ME_LOAD_ALL to session [{}]", session.getSessionId(), e);
        }
    }

    public void sendCharacterList(V2Session session, List<PlayerCharacter> characters) {
        try {
            V2Message ms = new V2Message(CMD_NOT_MAP);
            DataOutputStream ds = ms.writer();
            ds.writeByte(CMD_SELECT_PLAYER); // subCommand
            ds.writeByte(characters.size());

            for (PlayerCharacter c : characters) {
                ds.writeByte(c.getGender());
                ds.writeUTF(c.getName());
                ds.writeUTF(getSchoolName(c.getCombat().getClazz()));
                ds.writeByte(c.getState().getLevel());
                ds.writeShort(c.getHead());
                ds.writeShort(-1); // weapon
                ds.writeShort(-1); // body
                ds.writeShort(-1); // leg
            }
            ds.flush();
            session.send(ms);
            logger.info("Sent character list (count={}) to session [{}]", characters.size(), session.getSessionId());
        } catch (IOException e) {
            logger.error("Error sending character list to session [{}]", session.getSessionId(), e);
        }
    }

    public void sendServerDialog(V2Session session, String message) {
        try {
            V2Message ms = new V2Message(CMD_SERVER_DIALOG);
            DataOutputStream ds = ms.writer();
            ds.writeUTF(message);
            ds.flush();
            session.send(ms);
        } catch (IOException e) {
            logger.error("Error sending server dialog to session [{}]", session.getSessionId(), e);
        }
    }

    private String getSchoolName(byte clazz) {
        switch (clazz) {
            case 1: return "Hirosaki (Kiếm)";
            case 2: return "Ookaza (Phi Tiêu)";
            case 3: return "Haruna (Côn)";
            case 4: return "Hirosaki (Cung)";
            case 5: return "Ookaza (Đao)";
            case 6: return "Haruna (Quạt)";
            default: return "Chưa vào trường";
        }
    }
}

