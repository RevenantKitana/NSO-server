package com.nsoz2.network.controller;

import com.nsoz2.constants.CMD;
import com.nsoz2.network.Message;
import com.nsoz2.network.Session;
import com.nsoz2.util.Log;

/**
 * Controller chuyên trách hành trang, rương đồ, thú cưới, mua bán, nâng cấp, Shinwa và vĩ thú.
 */
public class InventoryPacketController extends AbstractPacketController {

    public InventoryPacketController(Session client) {
        super(client);
    }

    @Override
    public void handle(int command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.ITEM_USE:
                _char.useItem(mss);
                break;

            case CMD.ITEM_USE_CHANGEMAP:
                _char.useItemChangeMap(mss);
                break;

            case CMD.ITEM_BUY:
                _char.buyItem(mss);
                break;

            case CMD.ITEM_SALE:
                _char.saleItem(mss);
                break;

            case CMD.ITEM_BODY_TO_BAG:
                _char.itemBodyToBag(mss);
                break;

            case CMD.ITEM_BOX_TO_BAG:
                _char.itemBoxToBag(mss);
                break;

            case CMD.ITEM_BAG_TO_BOX:
                _char.itemBagToBox(mss);
                break;

            case CMD.ITEM_MON_TO_BAG:
                _char.itemMountToBag(mss);
                break;

            case CMD.ME_THROW:
                _char.throwItem(mss);
                break;

            case CMD.ITEMMAP_MYPICK:
                _char.pickItem(mss);
                break;

            case CMD.UPGRADE:
                _char.upgradeItem(mss);
                break;

            case CMD.SPLIT:
                _char.splitItem(mss);
                break;

            case CMD.UPPEARL:
                _char.upPearl(mss, true);
                break;

            case CMD.UPPEARL_LOCK:
                _char.upPearl(mss, false);
                break;

            case CMD.REQUEST_ITEM_INFO:
                _char.requestItemInfo(mss);
                break;

            case CMD.REQUEST_ITEM_PLAYER:
                _char.requestItemChar(mss);
                break;

            case CMD.NGOCKHAM:
                _char.ngocKham(mss);
                break;

            case CMD.DOI_OPTION:
                _char.dichChuyen(mss);
                break;

            case CMD.TINH_LUYEN:
                _char.tinhLuyen(mss);
                break;

            case CMD.LUYEN_THACH:
                _char.luyenThach(mss);
                break;

            case CMD.SEND_ITEM_TO_AUCTION:
                _char.sendToSaleItem(mss);
                break;

            case CMD.VIEW_ITEM_AUCTION:
                _char.requestViewDetails(mss);
                break;

            case CMD.BUY_ITEM_AUCTION:
                _char.buyItemAuction(mss);
                break;

            case CMD.REMOVE_VI_THU:
                _char.actionBijuu(mss);
                break;

            default:
                Log.debug(String.format("Client %d: InventoryPacketController unhandled: %d", client.id, command));
                break;
        }
    }

    /**
     * Xử lý gói tin vật phẩm trong CMD.NOT_MAP (tách đồ, chuyển hóa nâng cấp)
     */
    public void handleNotMap(byte command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.CONVERT_UPGRADE:
                _char.convertUpgrade(mss);
                break;

            case CMD.ITEM_SPLIT:
                _char.inputNumberSplit(mss);
                break;

            default:
                break;
        }
    }

    /**
     * Xử lý gói tin sắp xếp túi/rương và gửi/rút tiền (CMD.SUB_COMMAND)
     */
    public void handleSubCommand(byte command, Message mss) throws Exception {
        if (_char == null) {
            return;
        }

        switch (command) {
            case CMD.BAG_SORT:
                _char.bagSort();
                break;

            case CMD.BOX_SORT:
                _char.boxSort();
                break;

            case CMD.BOX_COIN_IN:
                _char.boxCoinIn(mss);
                break;

            case CMD.BOX_COIN_OUT:
                _char.boxCoinOut(mss);
                break;

            case CMD.REQUEST_ITEM:
                _char.requestItem(mss);
                break;

            default:
                break;
        }
    }
}

