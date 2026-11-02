package com.nsoz.v2.domain.world;

import com.nsoz.v2.adapter.network.V2Message;
import com.nsoz.v2.adapter.network.V2Session;
import com.nsoz.v2.domain.model.PlayerCharacter;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Isolated runtime map zone instance featuring Zone Hibernation.
 * Automatically suspends compute cycles when player count drops to zero.
 */
public class Zone {
    private static final Logger logger = LoggerFactory.getLogger(Zone.class);

    public static final byte CMD_PLAYER_MOVE = 1;
    public static final byte CMD_PLAYER_REMOVE = 2;
    public static final byte CMD_PLAYER_ADD = 3;
    public static final byte CMD_MAP_INFO = -18;
    public static final byte CMD_NPC_HP = -1;
    public static final byte CMD_NPC_DIE = -4;
    public static final byte CMD_NPC_MISS = 51;
    public static final byte CMD_PLAYER_ATTACK_NPC = 60;
    public static final byte CMD_NPC_IS_FIRE = 89;
    public static final byte CMD_NPC_IS_ICE = 90;
    public static final byte CMD_NPC_IS_WIND = 91;

    @Getter
    private final int id;
    @Getter
    private final GameMap map;
    private final Map<Long, V2Session> players = new ConcurrentHashMap<>();
    private final Map<Integer, com.nsoz.v2.domain.mob.Mob> monsters = new ConcurrentHashMap<>();

    @Getter
    private volatile boolean hibernated = true;

    public Zone(int id, GameMap map) {
        this.id = id;
        this.map = map;
        this.hibernated = true; // Starts in sleep mode
        initMonsters();
    }

    private void initMonsters() {
        if (map.getId() == 22) { // Làng Tone: 3 starter snails
            monsters.put(0, new com.nsoz.v2.domain.mob.Mob(0, 0, "Ốc sên", 1, 40, (short) 1908, (short) 264));
            monsters.put(1, new com.nsoz.v2.domain.mob.Mob(1, 0, "Ốc sên", 1, 40, (short) 1956, (short) 240));
            monsters.put(2, new com.nsoz.v2.domain.mob.Mob(2, 0, "Ốc sên", 1, 40, (short) 1980, (short) 240));
        }
    }

    public synchronized void join(V2Session session) {
        PlayerCharacter character = session.getActiveCharacter();
        if (character == null) {
            return;
        }

        // Zone Hibernation Wake-up
        if (hibernated) {
            hibernated = false;
            logger.info("Zone [{}-{}] WOKE UP from hibernation (Player [{}] entered)", map.getId(), id, character.getName());
        }

        character.getSpatial().setMapId(map.getId());
        character.getSpatial().setZoneId(id);

        // 1. Send Map & Tile info to the entering player
        sendMapInfo(session);

        // 2. Broadcast this player's presence to existing players in this zone
        V2Message addMsg = buildPlayerAddMessage(character);
        broadcast(addMsg, session.getSessionId());

        // 3. Send all existing players in this zone to the entering player
        for (V2Session existing : players.values()) {
            if (existing.getActiveCharacter() != null) {
                session.send(buildPlayerAddMessage(existing.getActiveCharacter()));
            }
        }

        // 4. Register session in this zone
        players.put(session.getSessionId(), session);
        logger.info("Player [{}] successfully joined Zone [{}-{}] (Total active players: {})",
                character.getName(), map.getId(), id, players.size());
    }

    public synchronized void leave(V2Session session) {
        players.remove(session.getSessionId());
        PlayerCharacter character = session.getActiveCharacter();
        if (character != null) {
            // Broadcast removal
            V2Message removeMsg = buildPlayerRemoveMessage(character.getId());
            broadcast(removeMsg, session.getSessionId());
            logger.info("Player [{}] left Zone [{}-{}] (Remaining players: {})",
                    character.getName(), map.getId(), id, players.size());
        }

        // Automatic Zone Hibernation when empty
        if (players.isEmpty()) {
            hibernated = true;
            logger.info("Zone [{}-{}] ENTERED HIBERNATION (Zero players remaining)", map.getId(), id);
        }
    }

    public void move(V2Session session, short newX, short newY) {
        PlayerCharacter character = session.getActiveCharacter();
        if (character == null) {
            return;
        }

        character.getSpatial().moveTo(newX, newY);

        // Broadcast movement update to other players in this zone
        V2Message moveMsg = buildPlayerMoveMessage(character.getId(), newX, newY);
        broadcast(moveMsg, session.getSessionId());
    }

    public void broadcast(V2Message msg, long excludeSessionId) {
        for (V2Session session : players.values()) {
            if (session.getSessionId() != excludeSessionId && session.getChannel() != null && session.getChannel().isActive()) {
                session.send(msg);
            }
        }
    }

    public Collection<V2Session> getPlayers() {
        return players.values();
    }

    public int getPlayerCount() {
        return players.size();
    }

    private void sendMapInfo(V2Session session) {
        PlayerCharacter c = session.getActiveCharacter();
        MapTemplate tpl = map.getTemplate();

        try {
            V2Message ms = new V2Message(CMD_MAP_INFO);
            DataOutputStream ds = ms.writer();
            ds.writeByte(tpl.getId());
            ds.writeByte(tpl.getTileId());
            ds.writeByte(tpl.getBgId());
            ds.writeByte(tpl.getType());
            ds.writeUTF(tpl.getName());
            ds.writeByte(id); // zoneId
            ds.writeShort(c.getSpatial().getX());
            ds.writeShort(c.getSpatial().getY());

            // Waypoints
            ds.writeByte(tpl.getWaypoints().size());
            for (Waypoint w : tpl.getWaypoints()) {
                ds.writeShort(w.getMinX());
                ds.writeShort(w.getMinY());
                ds.writeShort(w.getMaxX());
                ds.writeShort(w.getMaxY());
            }

            // Monsters count and metadata
            ds.writeByte(monsters.size());
            for (com.nsoz.v2.domain.mob.Mob mob : monsters.values()) {
                ds.writeBoolean(mob.getStatusEffects().isStunned()); // isDisable
                ds.writeBoolean(mob.getStatusEffects().isFrozen());  // isDontMove
                ds.writeBoolean(mob.getStatusEffects().isBurned());  // isFire
                ds.writeBoolean(mob.getStatusEffects().isFrozen());  // isIce
                ds.writeBoolean(mob.getStatusEffects().isStunned()); // isWind
                ds.writeByte(mob.getTemplateId());
                ds.writeByte(0); // sys
                ds.writeInt(mob.getHp());
                ds.writeByte(mob.getLevel());
                ds.writeInt(mob.getMaxHp());
                ds.writeShort(mob.getX());
                ds.writeShort(mob.getY());
                ds.writeByte(0); // status
                ds.writeByte(0); // levelBoss
                ds.writeBoolean(false); // isBoss
            }

            // Bu Nhin count
            ds.writeByte(0);

            // NPCs count
            ds.writeByte(0);

            // Items on ground count
            ds.writeByte(0);

            // TileMap Name & location stands
            ds.writeUTF(tpl.getName());
            ds.writeByte(0); // location stands

            ds.flush();
            session.send(ms);
        } catch (IOException e) {
            logger.error("Error sending MAP_INFO to session [{}]", session.getSessionId(), e);
        }
    }

    public static V2Message buildPlayerAddMessage(PlayerCharacter c) {
        V2Message ms = new V2Message(CMD_PLAYER_ADD);
        DataOutputStream ds = ms.writer();
        try {
            ds.writeInt(c.getId());
            ds.writeUTF(""); // Clan name
            ds.writeBoolean(false); // isInvisible
            ds.writeByte(c.getCombat().getPk());
            ds.writeByte(c.getCombat().getClazz());
            ds.writeByte(c.getGender());
            ds.writeShort(c.getHead());
            ds.writeUTF(c.getName());
            ds.writeInt(c.getState().getHp());
            ds.writeInt(c.getState().getMaxHp());
            ds.writeByte(c.getState().getLevel());
            ds.writeShort(-1); // weapon
            ds.writeShort(-1); // body
            ds.writeShort(-1); // leg
            ds.writeByte(-1);  // mobMe
            ds.writeShort(c.getSpatial().getX());
            ds.writeShort(c.getSpatial().getY());
            ds.writeShort(0);  // eff5buffhp
            ds.writeShort(0);  // eff5buffmp
            ds.writeByte(0);   // effect count
            ds.writeBoolean(true);  // isHuman
            ds.writeBoolean(false); // isNhanBan
            ds.writeShort(c.getHead());
            ds.writeShort(-1); // weapon
            ds.writeShort(-1); // body
            ds.writeShort(-1); // leg

            // 10 fashion slots
            for (int i = 0; i < 10; i++) {
                ds.writeShort(-1);
            }
            ds.flush();
        } catch (IOException e) {
            logger.error("Error building PLAYER_ADD for char [{}]", c.getName(), e);
        }
        return ms;
    }

    public static V2Message buildPlayerMoveMessage(int charId, short x, short y) {
        V2Message ms = new V2Message(CMD_PLAYER_MOVE);
        DataOutputStream ds = ms.writer();
        try {
            ds.writeInt(charId);
            ds.writeShort(x);
            ds.writeShort(y);
            ds.flush();
        } catch (IOException e) {
            logger.error("Error building PLAYER_MOVE for charId [{}]", charId, e);
        }
        return ms;
    }

    public static V2Message buildPlayerRemoveMessage(int charId) {
        V2Message ms = new V2Message(CMD_PLAYER_REMOVE);
        DataOutputStream ds = ms.writer();
        try {
            ds.writeInt(charId);
            ds.flush();
        } catch (IOException e) {
            logger.error("Error building PLAYER_REMOVE for charId [{}]", charId, e);
        }
        return ms;
    }

    public com.nsoz.v2.domain.mob.Mob getMob(int mobId) {
        return monsters.get(mobId);
    }

    public synchronized void handlePlayerAttackMob(V2Session session, int targetMobId, byte skillId) {
        PlayerCharacter character = session.getActiveCharacter();
        com.nsoz.v2.domain.mob.Mob mob = monsters.get(targetMobId);
        if (character == null || mob == null || mob.isDead()) {
            return;
        }

        // 1. Broadcast attack animation (CMD 60) to all players in the zone
        V2Message animMsg = new V2Message(CMD_PLAYER_ATTACK_NPC);
        try {
            DataOutputStream ds = animMsg.writer();
            ds.writeInt(character.getId());
            ds.writeByte(skillId);
            ds.writeByte(mob.getId());
            ds.flush();
            broadcast(animMsg, -1);
        } catch (IOException ignored) {}

        // 2. Prepare combat snapshots
        com.nsoz.v2.domain.combat.engine.CharacterStatsSnapshot attackerStats =
                com.nsoz.v2.domain.combat.engine.CharacterStatsSnapshot.createFromCharacter(character);

        int mobDef = 5;
        int mobEva = 10;
        int mobCritRes = 0;

        // Tri-State Effect Check: If mob is Frozen, halve its Evasion & CritRes
        if (mob.getStatusEffects().isFrozen()) {
            mobEva = mobEva / 2;
            mobCritRes = mobCritRes / 2;
        }

        // 3. Resolve combat with Single-Roll Resolution (Hyperbolic Curve)
        double effectProcChance = 0.35; // 35% chance to proc elemental status effect
        com.nsoz.v2.domain.combat.engine.SingleRollCombatResolver.CombatOutcome outcome =
                com.nsoz.v2.domain.combat.engine.SingleRollCombatResolver.resolve(
                        attackerStats.accuracy(), mobEva, attackerStats.criticalRate(), mobCritRes, effectProcChance);

        // 4. Handle MISS
        if (outcome.isMiss()) {
            V2Message missMsg = new V2Message(CMD_NPC_MISS);
            try {
                DataOutputStream ds = missMsg.writer();
                ds.writeByte(mob.getId());
                ds.writeInt(mob.getHp());
                ds.flush();
                broadcast(missMsg, -1);
            } catch (IOException ignored) {}
            return;
        }

        // 5. Handle HIT: Calculate damage with Multiplicative DR and Burn Amplification
        boolean isBurned = mob.getStatusEffects().isBurned();
        int damage = com.nsoz.v2.domain.combat.engine.DamageCalculator.calculateHitDamage(
                attackerStats, mobDef, 0, 1.0, 0, isBurned, outcome);

        // Apply elemental effect if triggered
        if (outcome.hasStatusEffect()) {
            applyElementalEffectToMob(mob, character.getCombat().getClazz());
        }

        mob.takeDamage(damage);

        // 6. Check Mob Death or HP Update
        if (mob.isDead()) {
            V2Message dieMsg = new V2Message(CMD_NPC_DIE);
            try {
                DataOutputStream ds = dieMsg.writer();
                ds.writeByte(mob.getId());
                ds.writeInt(damage);
                ds.writeBoolean(outcome.isCrit());
                ds.flush();
                broadcast(dieMsg, -1);
            } catch (IOException ignored) {}

            // Reward EXP to player
            character.getState().addExp(50);
            logger.info("Player [{}] killed Mob [{}] (Dealt {} dmg, Crit={}). EXP rewarded +50",
                    character.getName(), mob.getName(), damage, outcome.isCrit());

            // Schedule respawn after 3 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    mob.respawn();
                } catch (InterruptedException ignored) {}
            }).start();
        } else {
            V2Message hpMsg = new V2Message(CMD_NPC_HP);
            try {
                DataOutputStream ds = hpMsg.writer();
                ds.writeByte(mob.getId());
                ds.writeInt(mob.getHp());
                ds.writeInt(damage);
                ds.writeBoolean(outcome.isCrit());
                ds.writeByte(0); // levelBoss
                ds.flush();
                broadcast(hpMsg, -1);
            } catch (IOException ignored) {}

            logger.info("Player [{}] hit Mob [{}] for {} dmg (Crit={}, HP remaining: {}/{})",
                    character.getName(), mob.getName(), damage, outcome.isCrit(), mob.getHp(), mob.getMaxHp());
        }
    }

    private void applyElementalEffectToMob(com.nsoz.v2.domain.mob.Mob mob, byte clazz) {
        com.nsoz.v2.domain.combat.effect.StatusEffectType type;
        if (clazz == 6) {
            type = com.nsoz.v2.domain.combat.effect.StatusEffectType.FREEZE;
        } else if (clazz == 3 || clazz == 4 || clazz == 5) {
            type = com.nsoz.v2.domain.combat.effect.StatusEffectType.STUN;
        } else {
            type = com.nsoz.v2.domain.combat.effect.StatusEffectType.BURN;
        }

        mob.getStatusEffects().applyEffect(type, 3000); // 3 seconds duration

        byte cmd;
        if (type == com.nsoz.v2.domain.combat.effect.StatusEffectType.BURN) {
            cmd = CMD_NPC_IS_FIRE;
        } else if (type == com.nsoz.v2.domain.combat.effect.StatusEffectType.FREEZE) {
            cmd = CMD_NPC_IS_ICE;
        } else {
            cmd = CMD_NPC_IS_WIND;
        }

        V2Message effMsg = new V2Message(cmd);
        try {
            DataOutputStream ds = effMsg.writer();
            ds.writeByte(mob.getId());
            ds.writeBoolean(true);
            ds.flush();
            broadcast(effMsg, -1);
            logger.info("Applied Elemental Effect [{}] to Mob [{}]", type, mob.getId());
        } catch (IOException ignored) {}
    }
}

