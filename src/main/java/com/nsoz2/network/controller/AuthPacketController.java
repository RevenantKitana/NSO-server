package com.nsoz2.network.controller;

import com.nsoz2.constants.CMD;
import com.nsoz2.network.Message;
import com.nsoz2.network.Session;
import com.nsoz2.network.SpammerManager;
import com.nsoz2.util.Log;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller chuyên trách xác thực, chọn/tạo nhân vật, thiết lập phiên và mã bảo vệ.
 */
public class AuthPacketController extends AbstractPacketController {

    private static final int MAX_REQUESTS_GET_DATA = 20;
    private final Map<Byte, Integer> commandCount = new HashMap<>();

    public AuthPacketController(Session client) {
        super(client);
        commandCount.put(CMD.UPDATE_DATA, 0);
        commandCount.put(CMD.UPDATE_MAP, 0);
        commandCount.put(CMD.UPDATE_SKILL, 0);
        commandCount.put(CMD.UPDATE_ITEM, 0);
    }

    @Override
    public void handle(int command, Message mss) throws Exception {
        // Auth packets are primarily dispatched via handleNotLogin, handleNotMap, handleSubCommand
    }

    /**
     * Xử lý gói tin khi client chưa đăng nhập (CMD.NOT_LOGIN)
     */
    public void handleNotLogin(Message mss) throws Exception {
        if (mss == null || user != null) {
            return;
        }

        byte command = mss.reader().readByte();

        if (command == CMD.UPDATE_DATA || command == CMD.UPDATE_MAP || command == CMD.UPDATE_SKILL || command == CMD.UPDATE_ITEM) {
            commandCount.put(command, commandCount.getOrDefault(command, 0) + 1);
            if (commandCount.get(command) > MAX_REQUESTS_GET_DATA) {
                SpammerManager.getInstance().lock(client.getIPAddress());
                client.closeMessage();
                return;
            }
        }

        switch (command) {
            case CMD.LOGIN:
                client.login(mss);
                break;

            case CMD.CLIENT_INFO:
                client.setClientType(mss);
                break;

            default:
                Log.debug(String.format("Client %d: messageNotLogin: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý gói tin chuẩn bị vào map / chọn nhân vật (CMD.NOT_MAP)
     */
    public void handleNotMap(byte command, Message mss) throws Exception {
        if (command == CMD.UPDATE_SKILL) { // fix phá
            commandCount.put(command, commandCount.getOrDefault(command, 0) + 1);
            if (commandCount.get(command) > MAX_REQUESTS_GET_DATA) {
                SpammerManager.getInstance().lock(client.getIPAddress());
                client.closeMessage();
                return;
            }
        }

        switch (command) {
            case CMD.SELECT_PLAYER:
                if (_char == null && user != null) {
                    user.selectChar(mss);
                }
                break;

            case CMD.CREATE_PLAYER:
                if (_char == null && user != null) {
                    user.createCharacter(mss);
                }
                break;

            case CMD.UPDATE_DATA:
                if (service != null) {
                    service.updateData();
                }
                break;

            case CMD.UPDATE_MAP:
                if (service != null) {
                    service.updateMap();
                }
                break;

            case CMD.UPDATE_SKILL:
                if (service != null) {
                    service.updateSkill();
                }
                break;

            case CMD.UPDATE_ITEM:
                if (service != null) {
                    service.updateItem();
                }
                break;

            case CMD.REQUEST_ICON:
                if (service != null) {
                    service.requestIcon(mss);
                }
                break;

            case CMD.SERVER_ADD_MOB:
                if (service != null) {
                    byte type = mss.reader().readByte();
                    if (type == 0) {
                        service.sendImgEffectAuto(mss);
                    } else if (type == 1) {
                        service.sendEffectAutoData(mss);
                    }
                }
                break;

            case CMD.CLIENT_OK:
                this.client.clientOk();
                break;

            case CMD.ME_UPDATE_ACTIVE:
                if (_char != null) {
                    _char.updatePassProtect(mss);
                }
                break;

            case CMD.ME_OPEN_LOCK:
                if (_char != null) {
                    _char.openProtect(mss);
                }
                break;

            case CMD.ME_CLEAR_LOCK:
                if (_char != null) {
                    _char.clearProtect(mss);
                }
                break;

            default:
                Log.debug(String.format("Client %d: AuthPacketController notMap: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý gói tin RMS trong CMD.SUB_COMMAND
     */
    public void handleSubCommand(byte command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }
        switch (command) {
            case CMD.LOAD_RMS:
                _char.loadSkillShortcut(mss);
                break;

            case CMD.SAVE_RMS:
                _char.saveRms(mss);
                break;

            default:
                break;
        }
    }
}

