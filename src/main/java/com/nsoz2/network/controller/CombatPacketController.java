package com.nsoz2.network.controller;

import com.nsoz2.constants.CMD;
import com.nsoz2.network.Message;
import com.nsoz2.network.Session;
import com.nsoz2.util.Log;

/**
 * Controller chuyên trách chiến đấu, kỹ năng, tấn công NPC/Người, cừu sát, hiệu ứng và tăng điểm.
 */
public class CombatPacketController extends AbstractPacketController {

    public CombatPacketController(Session client) {
        super(client);
    }

    @Override
    public void handle(int command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.PLAYER_ATTACK_NPC:
                _char.attackMonster(mss);
                break;

            case CMD.PLAYER_ATTACK_PLAYER:
                _char.attackCharacter(mss);
                break;

            case CMD.PLAYER_ATTACK_P_N:
                _char.attackAllType(mss, 2);
                break;

            case CMD.PLAYER_ATTACK_N_P:
                _char.attackAllType(mss, 1);
                break;

            case CMD.USE_SKILL_MY_BUFF:
                _char.useSkillBuff(mss);
                break;

            case CMD.SKILL_SELECT:
                _char.selectSkill(mss);
                break;

            case CMD.ME_LIVE:
                _char.wakeUpFromDead(mss);
                break;

            case CMD.GET_EFFECT:
                if (service != null) {
                    byte type = mss.reader().readByte();
                    if (type == 1) {
                        service.sendImgEffect(mss);
                    } else if (type == 2) {
                        service.sendEffectData(mss);
                    }
                }
                break;

            case CMD.RANKED_MATCH:
                _char.requestRanked(mss);
                break;

            case CMD.ADD_CUU_SAT:
                _char.addCuuSat(mss);
                break;

            default:
                Log.debug(String.format("Client %d: CombatPacketController unhandled: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý gói tin chiến đấu trong CMD.SUB_COMMAND (hồi sinh, PK, nâng điểm)
     */
    public void handleSubCommand(byte command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.BUFF_LIVE:
                _char.hoiSinh(mss);
                break;

            case CMD.CHANGE_TYPE_PK:
                _char.changeTypePk(mss);
                break;

            case CMD.POTENTIAL_UP:
                _char.upPotential(mss);
                break;

            case CMD.SKILL_UP:
                _char.upSkill(mss);
                break;

            default:
                break;
        }
    }
}

