package com.nsoz2.network.controller;

import com.nsoz2.constants.CMD;
import com.nsoz2.network.Message;
import com.nsoz2.network.Session;
import com.nsoz2.util.Log;

/**
 * Controller chuyên trách không gian, di chuyển, chuyển map, đổi khu và nạp template bản đồ/NPC.
 */
public class SpatialPacketController extends AbstractPacketController {

    public SpatialPacketController(Session client) {
        super(client);
    }

    @Override
    public void handle(int command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.PLAYER_MOVE:
                _char.move(mss);
                break;

            case CMD.MAP_CHANGE:
                _char.requestChangeMap();
                break;

            case CMD.ZONE_CHANGE:
                _char.changeZone(mss);
                break;

            case CMD.OPEN_UI_ZONE:
                if (service != null) {
                    service.openUIZone();
                }
                break;

            case CMD.REQUEST_PLAYERS:
                _char.requestCharInfo(mss);
                break;

            case CMD.ME_BACK:
                _char.returnTownFromDead(mss);
                break;

            default:
                Log.debug(String.format("Client %d: SpatialPacketController unhandled: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý nạp template bản đồ / quái vật khi vào game (CMD.NOT_MAP)
     */
    public void handleNotMap(byte command, Message mss) throws Exception {
        if (_char == null || service == null) {
            return;
        }

        switch (command) {
            case CMD.REQUEST_MAPTEMPLATE:
                service.requestMapTemplate(mss);
                break;

            case CMD.REQUEST_NPCTEMPLATE:
                service.requestMobTemplate(mss);
                break;

            default:
                break;
        }
    }
}

