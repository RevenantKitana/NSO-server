package com.nsoz2.event.bus.listener;

import com.nsoz2.constants.TaskName;
import com.nsoz2.event.bus.EventBus;
import com.nsoz2.event.bus.event.ItemPickedEvent;
import com.nsoz2.event.bus.event.MonsterKilledEvent;
import com.nsoz2.event.bus.event.PlayerLevelUpEvent;
import com.nsoz2.item.Item;
import com.nsoz2.mob.Mob;
import com.nsoz2.model.Char;
import com.nsoz2.party.Group;
import com.nsoz2.task.TaskOrder;

import java.util.List;

/**
 * Listener xử lý tiến độ nhiệm vụ theo Observer Pattern (Spec 04 Mục 4.2)
 * Tách rời toàn bộ logic nhiệm vụ ra khỏi Mob.die(), Combat và Inventory.
 */
public class QuestEventListener {

    private static final QuestEventListener instance = new QuestEventListener();

    public static QuestEventListener getInstance() {
        return instance;
    }

    private QuestEventListener() {}

    /**
     * Khởi tạo và đăng ký lắng nghe các Domain Events
     */
    public void init() {
        EventBus.subscribe(MonsterKilledEvent.class, this::onMonsterKilled);
        EventBus.subscribe(ItemPickedEvent.class, this::onItemPicked);
        EventBus.subscribe(PlayerLevelUpEvent.class, this::onPlayerLevelUp);
    }

    /**
     * Xử lý tiến độ nhiệm vụ khi diệt quái/boss
     */
    public void onMonsterKilled(MonsterKilledEvent event) {
        Char killer = event.getKiller();
        Mob mob = event.getMob();
        if (killer == null || mob == null) {
            return;
        }

        // 1. Cập nhật TaskOrder (Nhiệm vụ hàng ngày & Nhiệm vụ Boss/Danh vọng)
        if (killer.taskOrders != null) {
            for (TaskOrder task : killer.taskOrders) {
                if (task.isComplete()) {
                    continue;
                }
                if (task.killId == mob.template.id) {
                    if (task.taskId == TaskOrder.TASK_DAY) {
                        task.updateTask(1);
                    }
                    if (task.taskId == TaskOrder.TASK_BOSS && mob.levelBoss == 3) {
                        task.updateTask(1);
                        Group group = killer.getGroup();
                        if (group != null && mob.zone != null) {
                            List<Char> chars = group.getCharsInZone(killer.mapId, mob.zone.id);
                            for (Char member : chars) {
                                if (member != null && member != killer && !member.isDead && member.taskOrders != null) {
                                    for (TaskOrder t2 : member.taskOrders) {
                                        if (!t2.isComplete() && t2.killId == mob.template.id && t2.taskId == TaskOrder.TASK_BOSS) {
                                            t2.updateTask(1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Cập nhật TaskMain (Nhiệm vụ cốt truyện chính tuyến)
        if (killer.taskMain != null) {
            if (killer.taskId != TaskName.NV_BAT_KHA_THI || ((killer.taskMain.index == 1 && mob.levelBoss == 1) || (killer.taskMain.index == 2 && mob.levelBoss == 2))) {
                killer.updateTaskKillMonster(mob);
                Group group = killer.getGroup();
                if (group != null && mob.zone != null) {
                    List<Char> chars = group.getCharsInZone(killer.mapId, mob.zone.id);
                    for (Char member : chars) {
                        if (member != null && member != killer && !member.isDead && member.taskMain != null) {
                            if (member.taskMain.taskId == killer.taskMain.taskId && member.taskMain.index == killer.taskMain.index) {
                                member.updateTaskKillMonster(mob);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Xử lý tiến độ nhiệm vụ khi nhặt vật phẩm
     */
    public void onItemPicked(ItemPickedEvent event) {
        Char player = event.getPlayer();
        Item item = event.getItem();
        if (player == null || item == null) {
            return;
        }
        player.updateTaskPickItem(item);
    }

    /**
     * Xử lý tiến độ nhiệm vụ khi thăng cấp
     */
    public void onPlayerLevelUp(PlayerLevelUpEvent event) {
        Char player = event.getPlayer();
        if (player == null) {
            return;
        }
        player.updateTaskLevelUp();
    }
}
