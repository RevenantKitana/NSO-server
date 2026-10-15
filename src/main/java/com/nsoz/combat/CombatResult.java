package com.nsoz.combat;

/**
 * Kết quả tính toán của một đòn đánh giao tranh.
 */
public class CombatResult {
    private final int damage;
    private final boolean isMiss;
    private final boolean isFatal;
    private final int reactDamage;
    private final boolean isIceReflect;

    public CombatResult(int damage, boolean isMiss, boolean isFatal, int reactDamage, boolean isIceReflect) {
        this.damage = damage;
        this.isMiss = isMiss;
        this.isFatal = isFatal;
        this.reactDamage = reactDamage;
        this.isIceReflect = isIceReflect;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isMiss() {
        return isMiss;
    }

    public boolean isFatal() {
        return isFatal;
    }

    public int getReactDamage() {
        return reactDamage;
    }

    public boolean isIceReflect() {
        return isIceReflect;
    }
}
