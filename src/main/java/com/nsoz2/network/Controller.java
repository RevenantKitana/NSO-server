package com.nsoz2.network;

import com.nsoz2.constants.CMD;
import com.nsoz2.model.Char;
import com.nsoz2.model.User;
import com.nsoz2.network.controller.AuthPacketController;
import com.nsoz2.network.controller.CombatPacketController;
import com.nsoz2.network.controller.InventoryPacketController;
import com.nsoz2.network.controller.SocialPacketController;
import com.nsoz2.network.controller.SpatialPacketController;
import com.nsoz2.util.Log;

/**
 * Controller Facade Router (Server V2 Modernized).
 * Điều phối toàn bộ luồng packet mạng tới 5 Domain Packet Controllers chuyên biệt.
 */
public class Controller implements IMessageHandler {

    private final Session client;
    private User user;
    private Char _char;
    private Service service;

    // 5 Domain Controllers
    private final AuthPacketController authController;
    private final SpatialPacketController spatialController;
    private final CombatPacketController combatController;
    private final InventoryPacketController inventoryController;
    private final SocialPacketController socialController;

    public Controller(Session client) {
        this.client = client;
        this.authController = new AuthPacketController(client);
        this.spatialController = new SpatialPacketController(client);
        this.combatController = new CombatPacketController(client);
        this.inventoryController = new InventoryPacketController(client);
        this.socialController = new SocialPacketController(client);
    }

    public void setUser(User us) {
        this.user = us;
        this.authController.setUser(us);
        this.spatialController.setUser(us);
        this.combatController.setUser(us);
        this.inventoryController.setUser(us);
        this.socialController.setUser(us);
    }

    public void setService(Service service) {
        this.service = service;
        this.authController.setService(service);
        this.spatialController.setService(service);
        this.combatController.setService(service);
        this.inventoryController.setService(service);
        this.socialController.setService(service);
    }

    public void setChar(Char _char) {
        this._char = _char;
        this.authController.setChar(_char);
        this.spatialController.setChar(_char);
        this.combatController.setChar(_char);
        this.inventoryController.setChar(_char);
        this.socialController.setChar(_char);
    }

    @Override
    public void onMessage(Message mss) {
        // 1. Kiểm tra chống Spam / Packet Flood
        int count = client.getPacketsSentInFiveSec().incrementAndGet();
        int maxPacketAllowed = 1000;
        int packetFloodThreshold = 5000;

        if (count > maxPacketAllowed) {
            long time = System.currentTimeMillis() - client.getLastPacketSentAt();
            if (time < packetFloodThreshold) {
                SpammerManager.getInstance().lock(client.getIPAddress());
                client.getPacketsSentInFiveSec().set(0);
                client.setLastPacketSentAt(System.currentTimeMillis());
                client.closeMessage();
                return;
            } else {
                client.getPacketsSentInFiveSec().set(0);
                client.setLastPacketSentAt(System.currentTimeMillis());
            }
        }

        if (mss == null) {
            return;
        }

        try {
            int command = mss.getCommand();

            // Kiểm tra trạng thái phiên đối với các gói tin yêu cầu đã vào game
            if (command != CMD.NOT_LOGIN && command != CMD.NOT_MAP && command != CMD.SUB_COMMAND) {
                if (user == null || _char == null || user.isCleaned || _char.isCleaned) {
                    return;
                }
            }

            // 2. Điều phối tới Controller tương ứng
            switch (command) {
                case CMD.NEW_MESSAGE:
                    newMessage(mss);
                    break;

                case CMD.NOT_LOGIN:
                    messageNotLogin(mss);
                    break;

                case CMD.NOT_MAP:
                    messageNotMap(mss);
                    break;

                case CMD.SUB_COMMAND:
                    messageSubCommand(mss);
                    break;

                // --- SPATIAL DOMAIN ---
                case CMD.PLAYER_MOVE:
                case CMD.MAP_CHANGE:
                case CMD.ZONE_CHANGE:
                case CMD.OPEN_UI_ZONE:
                case CMD.REQUEST_PLAYERS:
                case CMD.ME_BACK:
                    spatialController.handle(command, mss);
                    break;

                // --- COMBAT DOMAIN ---
                case CMD.PLAYER_ATTACK_NPC:
                case CMD.PLAYER_ATTACK_PLAYER:
                case CMD.PLAYER_ATTACK_P_N:
                case CMD.PLAYER_ATTACK_N_P:
                case CMD.USE_SKILL_MY_BUFF:
                case CMD.SKILL_SELECT:
                case CMD.ME_LIVE:
                case CMD.GET_EFFECT:
                case CMD.RANKED_MATCH:
                case CMD.ADD_CUU_SAT:
                    combatController.handle(command, mss);
                    break;

                // --- INVENTORY DOMAIN ---
                case CMD.ITEM_USE:
                case CMD.ITEM_USE_CHANGEMAP:
                case CMD.ITEM_BUY:
                case CMD.ITEM_SALE:
                case CMD.ITEM_BODY_TO_BAG:
                case CMD.ITEM_BOX_TO_BAG:
                case CMD.ITEM_BAG_TO_BOX:
                case CMD.ITEM_MON_TO_BAG:
                case CMD.ME_THROW:
                case CMD.ITEMMAP_MYPICK:
                case CMD.UPGRADE:
                case CMD.SPLIT:
                case CMD.UPPEARL:
                case CMD.UPPEARL_LOCK:
                case CMD.REQUEST_ITEM_INFO:
                case CMD.REQUEST_ITEM_PLAYER:
                case CMD.NGOCKHAM:
                case CMD.DOI_OPTION:
                case CMD.TINH_LUYEN:
                case CMD.LUYEN_THACH:
                case CMD.SEND_ITEM_TO_AUCTION:
                case CMD.VIEW_ITEM_AUCTION:
                case CMD.BUY_ITEM_AUCTION:
                case CMD.REMOVE_VI_THU:
                    inventoryController.handle(command, mss);
                    break;

                // --- SOCIAL DOMAIN ---
                case CMD.CHAT_MAP:
                case CMD.CHAT_PRIVATE:
                case CMD.CHAT_SERVER:
                case CMD.CHAT_CLAN:
                case CMD.CHAT_PARTY:
                case CMD.PLEASE_INPUT_PARTY:
                case CMD.ACCEPT_PLEASE_PARTY:
                case CMD.PARTY_INVITE:
                case CMD.PARTY_ACCEPT:
                case CMD.PARTY_CANCEL:
                case CMD.PARTY_OUT:
                case CMD.TRADE_INVITE:
                case CMD.TRADE_INVITE_ACCEPT:
                case CMD.TRADE_LOCK_ITEM:
                case CMD.TRADE_ACCEPT:
                case CMD.TRADE_CANCEL:
                case CMD.FRIEND_INVITE:
                case CMD.TEST_INVITE:
                case CMD.TEST_INVITE_ACCEPT:
                case CMD.TEST_DUN_INVITE:
                case CMD.TEST_DUN_LIST:
                case CMD.TEST_GT_INVITE:
                case CMD.MENU:
                case CMD.OPEN_UI_MENU:
                case CMD.OPEN_UI_CONFIRM_ID:
                case CMD.OPEN_TEXT_BOX_ID:
                case CMD.VIEW_INFO:
                case CMD.TASK_GET:
                    socialController.handle(command, mss);
                    break;

                default:
                    Log.debug("CMD unhandled in V2 Controller: " + command);
                    break;
            }
        } catch (Exception e) {
            Log.logException("Lỗi nhận message từ client: ", Controller.class, e);
        }
    }

    @Override
    public void newMessage(Message mss) {
        try {
            socialController.handleNewMessage(mss);
        } catch (Exception e) {
            Log.logException("Lỗi nhận newMessage từ client: ", Controller.class, e);
        }
    }

    @Override
    public void messageNotLogin(Message mss) {
        try {
            authController.handleNotLogin(mss);
        } catch (Exception e) {
            Log.logException("Lỗi nhận messageNotLogin từ client: ", Controller.class, e);
        }
    }

    @Override
    public void messageNotMap(Message mss) {
        if (mss == null || user == null || user.isCleaned) {
            return;
        }

        try {
            byte command = mss.reader().readByte();

            switch (command) {
                // Auth & Character setup & Locks
                case CMD.SELECT_PLAYER:
                case CMD.CREATE_PLAYER:
                case CMD.UPDATE_DATA:
                case CMD.UPDATE_MAP:
                case CMD.UPDATE_SKILL:
                case CMD.UPDATE_ITEM:
                case CMD.REQUEST_ICON:
                case CMD.SERVER_ADD_MOB:
                case CMD.CLIENT_OK:
                case CMD.ME_UPDATE_ACTIVE:
                case CMD.ME_OPEN_LOCK:
                case CMD.ME_CLEAR_LOCK:
                    authController.handleNotMap(command, mss);
                    break;

                // Spatial
                case CMD.REQUEST_MAPTEMPLATE:
                case CMD.REQUEST_NPCTEMPLATE:
                    spatialController.handleNotMap(command, mss);
                    break;

                // Inventory
                case CMD.CONVERT_UPGRADE:
                case CMD.ITEM_SPLIT:
                    inventoryController.handleNotMap(command, mss);
                    break;

                // Social & Clan
                case CMD.REQUEST_CLAN_LOG:
                case CMD.REQUEST_CLAN_INFO:
                case CMD.REQUEST_CLAN_MEMBER:
                case CMD.REQUEST_CLAN_ITEM:
                case CMD.REWARD_PB:
                case CMD.REWARD_CT:
                case CMD.CLAN_CHANGE_ALERT:
                case CMD.CLAN_CHANGE_TYPE:
                case CMD.CLAN_MOVEOUT_MEM:
                case CMD.CLAN_OUT:
                case CMD.CLAN_UP_LEVEL:
                case CMD.INPUT_COIN_CLAN:
                case CMD.OUTPUT_COIN_CLAN:
                case CMD.INVITE_CLANDUN:
                case CMD.MOI_GTC:
                case CMD.MOI_TATCA_GTC:
                case CMD.GO_GTCHIEN:
                case CMD.CLAN_USE_ITEM:
                case CMD.LAT_HINH:
                case CMD.OPEN_CLAN_ITEM:
                case CMD.CLAN_SEND_ITEM:
                    socialController.handleNotMap(command, mss);
                    break;

                default:
                    Log.debug(String.format("Client %d: messageNotMap unhandled: %d", client.id, command));
                    break;
            }
        } catch (Exception e) {
            Log.logException("Lỗi nhận messageNotMap từ client: ", Controller.class, e);
        }
    }

    @Override
    public void messageSubCommand(Message mss) {
        if (mss == null || user == null || _char == null || user.isCleaned || _char.isCleaned) {
            return;
        }

        try {
            byte command = mss.reader().readByte();

            switch (command) {
                // Auth
                case CMD.LOAD_RMS:
                case CMD.SAVE_RMS:
                    authController.handleSubCommand(command, mss);
                    break;

                // Combat
                case CMD.BUFF_LIVE:
                case CMD.CHANGE_TYPE_PK:
                case CMD.POTENTIAL_UP:
                case CMD.SKILL_UP:
                    combatController.handleSubCommand(command, mss);
                    break;

                // Inventory
                case CMD.BAG_SORT:
                case CMD.BOX_SORT:
                case CMD.BOX_COIN_IN:
                case CMD.BOX_COIN_OUT:
                case CMD.REQUEST_ITEM:
                    inventoryController.handleSubCommand(command, mss);
                    break;

                // Social
                case CMD.CLAN_INVITE:
                case CMD.CLAN_ACCEPT_INVITE:
                case CMD.CREATE_PARTY:
                case CMD.LOCK_PARTY:
                case CMD.FIND_PARTY:
                case CMD.MOVE_MEMBER:
                case CMD.CHANGE_TEAMLEADER:
                case CMD.FRIEND_REMOVE:
                case CMD.ENEMIES_REMOVE:
                case CMD.REQUEST_FRIEND:
                case CMD.REQUEST_ENEMIES:
                    socialController.handleSubCommand(command, mss);
                    break;

                default:
                    Log.debug(String.format("Client %d: messageSubCommand unhandled: %d", client.id, command));
                    break;
            }
        } catch (Exception e) {
            Log.logException("Lỗi nhận messageSubCommand từ client: ", Controller.class, e);
        }
    }

    @Override
    public void onConnectionFail() {
        Log.debug(String.format("Client %d: Kết nối thất bại!", client.id));
    }

    @Override
    public void onDisconnected() {
        Log.debug(String.format("Client %d: Mất kết nối!", client.id));
    }

    @Override
    public void onConnectOK() {
        System.out.printf("Client %d: Kết nối thành công!%n", client.id);
        Log.debug(String.format("Client %d: Kết nối thành công!", client.id));
    }
}
