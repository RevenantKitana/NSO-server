package com.nsoz.model;

import com.nsoz.constants.ItemName;
import com.nsoz.constants.NpcName;
import com.nsoz.constants.SQLStatement;
import com.nsoz.db.jdbc.DbManager;
import com.nsoz.item.Item;
import com.nsoz.util.Log;
import com.nsoz.util.NinjaUtils;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Giveaways {
    @Getter
    private static final Giveaways instance = new Giveaways();

    public void give(Char player) {
        try (Connection conn = DbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    SQLStatement.GET_GIVEAWAYS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, player.name);
            stmt.setString(2, player.name);
            ResultSet res = stmt.executeQuery();
            try {
                if (res.first()) {
                    int id = res.getInt("id");
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
                            Log.logException("Lỗi parse JSON items Giveaways: " + itemsStr, Giveaways.class, e);
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
                    sb.append("Chúc mừng, bạn đã được tặng:").append("\n\n");

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

                    JSONArray used = (JSONArray) (new JSONParser().parse(res.getString("used")));
                    used.add(player.name);
                    player.getService().showAlert("Phần quà", sb.toString());
                    DbManager.executeUpdate(SQLStatement.UPDATE_USED, used.toJSONString(), id);
                    player.saveData();
                    if (player.user != null) {
                        player.user.saveData();
                    }
                } else {
                    player.getService().npcChat(NpcName.ADMIN, "Bạn phải đạt huy hiệu Fancung trên Fanpage.");
                }
            } catch (Exception e) {
                Log.logException("Lỗi kiểm tra sử dụng Giveaways(1): ", Giveaways.class, e);
                player.getService().serverDialog("Đã xảy ra lỗi khi nhận quà tặng.");
            } finally {
                res.close();
                stmt.close();
            }
        } catch (Exception ex) {
            Log.logException("Lỗi kiểm tra sử dụng Giveaways(2): ", Giveaways.class, ex);
        }
    }
}
