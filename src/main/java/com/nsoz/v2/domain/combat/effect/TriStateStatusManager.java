package com.nsoz.v2.domain.combat.effect;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the Tri-Element Status Effects on a Combat Entity (Player or Mob).
 * Supports simultaneous stacking of all 3 orthogonal states with independent timers.
 */
public class TriStateStatusManager {

    @Getter
    public static class ActiveEffect {
        private final StatusEffectType type;
        private volatile long expireAt;

        public ActiveEffect(StatusEffectType type, int durationMs) {
            this.type = type;
            this.expireAt = System.currentTimeMillis() + durationMs;
        }

        public void refresh(int durationMs) {
            this.expireAt = Math.max(this.expireAt, System.currentTimeMillis() + durationMs);
        }

        public boolean isExpired(long now) {
            return now >= expireAt;
        }
    }

    private final Map<StatusEffectType, ActiveEffect> activeEffects = new ConcurrentHashMap<>();

    public boolean applyEffect(StatusEffectType type, int durationMs) {
        ActiveEffect existing = activeEffects.get(type);
        if (existing != null && !existing.isExpired(System.currentTimeMillis())) {
            existing.refresh(durationMs);
            return false; // Refreshed duration
        }
        activeEffects.put(type, new ActiveEffect(type, durationMs));
        return true; // Newly applied
    }

    public boolean hasEffect(StatusEffectType type) {
        ActiveEffect effect = activeEffects.get(type);
        if (effect == null) {
            return false;
        }
        if (effect.isExpired(System.currentTimeMillis())) {
            activeEffects.remove(type);
            return false;
        }
        return true;
    }

    public boolean isBurned() {
        return hasEffect(StatusEffectType.BURN);
    }

    public boolean isFrozen() {
        return hasEffect(StatusEffectType.FREEZE);
    }

    public boolean isStunned() {
        return hasEffect(StatusEffectType.STUN);
    }

    public void removeEffect(StatusEffectType type) {
        activeEffects.remove(type);
    }

    public void clearAll() {
        activeEffects.clear();
    }
}

