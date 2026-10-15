package com.nsoz.model.stat;

/**
 * Đại diện cho một giá trị thay đổi chỉ số (Modifier).
 */
public class StatModifier {
    private final StatType statType;
    private final ModType modType;
    private final int value;

    public StatModifier(StatType statType, ModType modType, int value) {
        this.statType = statType;
        this.modType = modType;
        this.value = value;
    }

    public StatType getStatType() {
        return statType;
    }

    public ModType getModType() {
        return modType;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StatModifier{" +
                "statType=" + statType +
                ", modType=" + modType +
                ", value=" + value +
                '}';
    }
}
