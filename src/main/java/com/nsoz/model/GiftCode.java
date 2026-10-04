/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz.model;

import com.nsoz.constants.ItemName;
import com.nsoz.constants.SQLStatement;
import com.nsoz.db.jdbc.DbManager;
import com.nsoz.item.Item;
import com.nsoz.item.ItemManager;
import com.nsoz.item.ItemTemplate;
import com.nsoz.server.Config;
import com.nsoz.util.Log;
import com.nsoz.util.NinjaUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class GiftCode {

    private static final GiftCode instance = new GiftCode();

    public static GiftCode getInstance() {
        return instance;
    }

    public void use(Char player, String code) {
        if (code == null) {
            player.getService().serverDialog("Mã quà tặng không hợp lệ.");
            return;
        }
        code = code.trim();
        if (code.isEmpty() || code.length() > 100) {
            player.getService().serverDialog("Mã quà tặng không hợp lệ.");
            return;
        }

        try (Connection conn = DbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    SQLStatement.GET_GIFT_CODE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, code);
            stmt.setInt(2, Config.getInstance().getServerID());
            ResultSet res = stmt.executeQuery();
            try {
                if (!res.first()) {
                    player.getService().serverDialog("Mã quà tặng không tồn tại hoặc đã hết hạn.");
                    return;
                }

                int id = res.getInt("id");
                byte status = res.getByte("status");
                byte type = res.getByte("type");
                byte serverId = res.getByte("server_id");

                if (status == 1) {
                    player.getService().serverDialog("Mã quà tặng đã được sử dụng");
                    return;
                } else if (type == 1 && isUsedGiftCode(player, code)) {
                    player.getService().serverDialog("Mỗi người chỉ được sử dụng 1 lần.");
                    return;
                } else if (player.user.session.getCountUseGiftCode() >= 100) {
                    player.getService().serverDialog("Mỗi ngày chỉ có thể nhập tối đa 100 mã quà tặng.");
                    return;
                }

                int gold = res.getInt("gold");
                int yen = res.getInt("yen");
                int coin = res.getInt("coin");

                String itemsStr = res.getString("items");
                JSONArray arrItem = new JSONArray();
                if (itemsStr != null && !itemsStr.trim().isEmpty()) {
                    try {
                        Object parsed = new JSONParser().parse(itemsStr);
                        if (parsed instanceof JSONArray) {
                            arrItem = (JSONArray) parsed;
                        }
                    } catch (Exception e) {
                        Log.logException("Lỗi parse JSON items giftcode: " + itemsStr, GiftCode.class, e);
                    }
                }

                List<Item> itemsToAdd = new ArrayList<>();
                int size = arrItem.size();
                for (int i = 0; i < size; i++) {
                    Object el = arrItem.get(i);
                    if (!(el instanceof JSONObject)) {
                        continue;
                    }
                    JSONObject itemObj = (JSONObject) el;
                    Item newItem = new Item(itemObj);
                    if (newItem.template == null) {
                        continue;
                    }

                    if (itemObj.containsKey("upgrade")) {
                        try {
                            int up = Integer.parseInt(itemObj.get("upgrade").toString());
                            if (up > 0) {
                                newItem.upgrade = (byte) up;
                                newItem.next(up);
                            }
                        } catch (Exception ignored) {}
                    }

                    if (itemObj.containsKey("sys")) {
                        try {
                            newItem.sys = Byte.parseByte(itemObj.get("sys").toString());
                        } catch (Exception ignored) {}
                    }

                    if (itemObj.containsKey("isLock")) {
                        try {
                            newItem.isLock = Boolean.parseBoolean(itemObj.get("isLock").toString());
                        } catch (Exception ignored) {}
                    } else if (itemObj.containsKey("lock")) {
                        try {
                            newItem.isLock = Boolean.parseBoolean(itemObj.get("lock").toString());
                        } catch (Exception ignored) {}
                    }

                    // Hạn sử dụng
                    if (newItem.template.id == ItemName.V_VIP) {
                        long expire = System.currentTimeMillis() + (long) (86400000L * 3);
                        newItem.expire = expire;
                    } else if (itemObj.containsKey("expire")) {
                        try {
                            long expVal = Long.parseLong(itemObj.get("expire").toString());
                            if (expVal <= 0 || expVal == -1) {
                                newItem.expire = -1;
                            } else if (expVal < 1000000000000L) {
                                newItem.expire = System.currentTimeMillis() + expVal;
                            } else {
                                newItem.expire = expVal;
                            }
                        } catch (Exception ignored) {
                            newItem.expire = -1;
                        }
                    } else {
                        newItem.expire = -1;
                    }

                    int qty = newItem.getQuantity();
                    if (qty <= 0) {
                        qty = 1;
                    }

                    if (newItem.template.isUpToUp) {
                        newItem.setQuantity(qty);
                        itemsToAdd.add(newItem);
                    } else {
                        for (int q = 0; q < qty; q++) {
                            Item singleItem = new Item(itemObj);
                            if (itemObj.containsKey("upgrade")) {
                                try {
                                    int up = Integer.parseInt(itemObj.get("upgrade").toString());
                                    if (up > 0) {
                                        singleItem.upgrade = (byte) up;
                                        singleItem.next(up);
                                    }
                                } catch (Exception ignored) {}
                            }
                            if (itemObj.containsKey("sys")) {
                                try {
                                    singleItem.sys = Byte.parseByte(itemObj.get("sys").toString());
                                } catch (Exception ignored) {}
                            }
                            if (itemObj.containsKey("isLock")) {
                                try {
                                    singleItem.isLock = Boolean.parseBoolean(itemObj.get("isLock").toString());
                                } catch (Exception ignored) {}
                            } else if (itemObj.containsKey("lock")) {
                                try {
                                    singleItem.isLock = Boolean.parseBoolean(itemObj.get("lock").toString());
                                } catch (Exception ignored) {}
                            }
                            singleItem.expire = newItem.expire;
                            singleItem.setQuantity(1);
                            itemsToAdd.add(singleItem);
                        }
                    }
                }

                // Kiểm tra số lượng ô trống trong hành trang
                int requiredSlots = 0;
                for (Item item : itemsToAdd) {
                    if (item.template.isUpToUp) {
                        int idx = player.getIndexItemByIdInBag(item.id, item.isLock);
                        if (idx == -1 || player.bag[idx] == null || player.bag[idx].hasExpire()) {
                            requiredSlots++;
                        }
                    } else {
                        requiredSlots++;
                    }
                }

                if (requiredSlots > player.getSlotNull()) {
                    player.getService().serverDialog("Hành trang của bạn không đủ chỗ trống (cần " + requiredSlots + " ô trống).");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Chúc mừng, bạn đã nhận được quà tặng:").append("\n\n");

                if (gold > 0) {
                    player.addGold(gold);
                    sb.append(String.format("- %s lượng", NinjaUtils.getCurrency(gold))).append("\n");
                }

                if (yen > 0) {
                    player.addYen(yen);
                    sb.append(String.format("- %s yên", NinjaUtils.getCurrency(yen))).append("\n");
                }

                if (coin > 0) {
                    player.addCoin(coin);
                    sb.append(String.format("- %s xu", NinjaUtils.getCurrency(coin))).append("\n");
                }

                for (Item item : itemsToAdd) {
                    player.addItemToBag(item);
                    if (item.template.isUpToUp) {
                        sb.append(String.format("- x%s %s", NinjaUtils.getCurrency(item.getQuantity()), item.template.name)).append("\n");
                    } else {
                        String upStr = item.upgrade > 0 ? (" +" + item.upgrade) : "";
                        sb.append(String.format("- x1 %s%s", item.template.name, upStr)).append("\n");
                    }
                }

                player.user.session.addUseGiftCode();
                player.getService().showAlert("Mã quà tặng", sb.toString());
                player.saveData();
                if (player.user != null) {
                    player.user.saveData();
                }

                addUsedGiftCode(player, code);
                if (type == 0) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    res.updateByte("status", (byte) 1);
                    res.updateTimestamp("updated_at", timestamp);
                    res.updateRow();
                }
            } finally {
                res.close();
                stmt.close();
            }
        } catch (Exception ex) {
            Log.logException("Lỗi sử dụng GiftCode: ", GiftCode.class, ex);
            player.getService().serverDialog("Đã xảy ra lỗi khi nhận mã quà tặng. Vui lòng thử lại sau.");
        }
    }

    public boolean isUsedGiftCode(Char player, String giftCode) {
        try (Connection conn = DbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    SQLStatement.CHECK_EXIST_USED_GIFT_CODE, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, giftCode);
            stmt.setInt(2, player.id);
            stmt.setInt(3, player.user.id);
            ResultSet res = stmt.executeQuery();
            try {
                if (res.first()) {
                    return true;
                }
            } finally {
                res.close();
                stmt.close();
            }
        } catch (SQLException e) {
            Log.logException("Lỗi kiểm tra sử dụng GiftCode: ", GiftCode.class, e);
        }
        return false;
    }

    public void addUsedGiftCode(Char player, String giftCode) {
        try (Connection conn = DbManager.getConnection()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            PreparedStatement stmt = conn.prepareStatement(SQLStatement.INSERT_USED_GIFT_CODE);
            stmt.setInt(1, player.id);
            stmt.setInt(2, player.user.id);
            stmt.setString(3, giftCode);
            stmt.setTimestamp(4, timestamp);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            Log.logException("Lỗi thêm đã sử dụng GiftCode: ", GiftCode.class, e);
        }
    }

}
