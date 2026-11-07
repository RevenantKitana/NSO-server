package com.nsoz2.network.controller;

import com.nsoz2.constants.CMD;
import com.nsoz2.network.Message;
import com.nsoz2.network.Session;
import com.nsoz2.util.Log;

/**
 * Controller chuyên trách mạng xã hội: Kênh chat, Bang hội (Clan), Tổ đội (Party), Giao dịch, Bạn bè và Menu NPC.
 */
public class SocialPacketController extends AbstractPacketController {

    public SocialPacketController(Session client) {
        super(client);
    }

    @Override
    public void handle(int command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            // Chat
            case CMD.CHAT_MAP:
                if (user != null) {
                    _char.chatPublic(mss);
                }
                break;

            case CMD.CHAT_PRIVATE:
                _char.chatPrivate(mss);
                break;

            case CMD.CHAT_SERVER:
                _char.chatGlobal(mss);
                break;

            case CMD.CHAT_CLAN:
                _char.chatClan(mss);
                break;

            case CMD.CHAT_PARTY:
                _char.chatParty(mss);
                break;

            // Party
            case CMD.PLEASE_INPUT_PARTY:
                _char.pleaseInputParty(mss);
                break;

            case CMD.ACCEPT_PLEASE_PARTY:
                _char.acceptPleaseParty(mss);
                break;

            case CMD.PARTY_INVITE:
                _char.addParty(mss);
                break;

            case CMD.PARTY_ACCEPT:
                _char.addPartyAccept(mss);
                break;

            case CMD.PARTY_CANCEL:
                _char.addPartyCancel(mss);
                break;

            case CMD.PARTY_OUT:
                _char.outParty();
                break;

            // Trade
            case CMD.TRADE_INVITE:
                _char.tradeInvite(mss);
                break;

            case CMD.TRADE_INVITE_ACCEPT:
                _char.acceptInviteTrade(mss);
                break;

            case CMD.TRADE_LOCK_ITEM:
                _char.tradeItemLock(mss);
                break;

            case CMD.TRADE_ACCEPT:
                _char.tradeAccept();
                break;

            case CMD.TRADE_CANCEL:
                _char.tradeClose();
                break;

            // Friends & Test Battles
            case CMD.FRIEND_INVITE:
                _char.addFriend(mss);
                break;

            case CMD.TEST_INVITE:
                if (!_char.isTest) {
                    _char.testInvite(mss);
                }
                break;

            case CMD.TEST_INVITE_ACCEPT:
                _char.testAccept(mss);
                break;

            case CMD.TEST_DUN_INVITE:
                _char.acceptInviteTestDun(mss);
                break;

            case CMD.TEST_DUN_LIST:
                _char.requestMatchInfo(mss);
                break;

            case CMD.TEST_GT_INVITE:
                _char.acceptInviteWarClan(mss);
                break;

            // UI & Menu & Dialogs
            case CMD.MENU:
                _char.menu(mss);
                break;

            case CMD.OPEN_UI_MENU:
                _char.openMenu(mss);
                break;

            case CMD.OPEN_UI_CONFIRM_ID:
                _char.confirmID(mss);
                break;

            case CMD.OPEN_TEXT_BOX_ID:
                _char.input(mss);
                break;

            case CMD.VIEW_INFO:
                _char.viewInfo(mss);
                break;

            case CMD.TASK_GET:
                _char.getTask(mss);
                break;

            default:
                Log.debug(String.format("Client %d: SocialPacketController unhandled: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý gói tin tin nhắn hệ thống (CMD.NEW_MESSAGE)
     */
    public void handleNewMessage(Message mss) throws Exception {
        if (_char == null || mss == null) {
            return;
        }
        byte command = mss.reader().readByte();
        switch (command) {
            case 0:
                _char.cancelClan();
                break;

            default:
                Log.debug(String.format("Client %d: newMessage: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý gói tin bang hội và hoạt động trong CMD.NOT_MAP
     */
    public void handleNotMap(byte command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.REQUEST_CLAN_LOG:
                if (service != null) {
                    service.writeLog();
                }
                break;

            case CMD.REQUEST_CLAN_INFO:
                if (service != null) {
                    service.requestClanInfo();
                }
                break;

            case CMD.REQUEST_CLAN_MEMBER:
                if (service != null) {
                    service.requestClanMember();
                }
                break;

            case CMD.REQUEST_CLAN_ITEM:
                if (service != null) {
                    service.requestClanItem();
                }
                break;

            case CMD.REWARD_PB:
                _char.rewardDungeon();
                break;

            case CMD.REWARD_CT:
                _char.rewardCT();
                break;

            case CMD.CLAN_CHANGE_ALERT:
                _char.changeClanAlert(mss);
                break;

            case CMD.CLAN_CHANGE_TYPE:
                _char.changeClanType(mss);
                break;

            case CMD.CLAN_MOVEOUT_MEM:
                _char.moveOutClan(mss);
                break;

            case CMD.CLAN_OUT:
                _char.outClan();
                break;

            case CMD.CLAN_UP_LEVEL:
                _char.clanUpLevel();
                break;

            case CMD.INPUT_COIN_CLAN:
                _char.inputCoinClan(mss);
                break;

            case CMD.OUTPUT_COIN_CLAN:
                _char.outputCoinClan(mss);
                break;

            case CMD.INVITE_CLANDUN:
                _char.inviteTerritory(mss);
                break;

            case CMD.MOI_GTC:
                _char.inviteMemberGTC(mss);
                break;

            case CMD.MOI_TATCA_GTC:
                break;

            case CMD.GO_GTCHIEN:
                _char.memberAppectGTC();
                break;

            case CMD.CLAN_USE_ITEM:
                _char.clanUseItem(mss);
                break;

            case CMD.LAT_HINH:
                _char.selectCard(mss);
                break;

            case CMD.OPEN_CLAN_ITEM:
                _char.unlockClanItem();
                break;

            case CMD.CLAN_SEND_ITEM:
                _char.sendClanItem(mss);
                break;

            default:
                break;
        }
    }

    /**
     * Xử lý gói tin gia tộc, tổ đội, bạn bè/thù oán trong CMD.SUB_COMMAND
     */
    public void handleSubCommand(byte command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.CLAN_INVITE:
                _char.clanInvite(mss);
                break;

            case CMD.CLAN_ACCEPT_INVITE:
                _char.acceptInviteClan(mss);
                break;

            case CMD.CREATE_PARTY:
                _char.createGroup();
                break;

            case CMD.LOCK_PARTY:
                _char.lockParty(mss);
                break;

            case CMD.FIND_PARTY:
                _char.openFindParty();
                break;

            case CMD.MOVE_MEMBER:
                _char.moveMember(mss);
                break;

            case CMD.CHANGE_TEAMLEADER:
                _char.changeTeamLeader(mss);
                break;

            case CMD.FRIEND_REMOVE:
                _char.removeFriend(mss);
                break;

            case CMD.ENEMIES_REMOVE:
                _char.removeEnemy(mss);
                break;

            case CMD.REQUEST_FRIEND:
                if (service != null) {
                    service.requestFriend();
                }
                break;

            case CMD.REQUEST_ENEMIES:
                if (service != null) {
                    service.requestEnemy();
                }
                break;

            default:
                break;
        }
    }
}

