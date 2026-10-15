package com.nsoz.model.stat;

import java.util.EnumMap;
import java.util.Map;

/**
 * Bộ gom chỉ số (Stat Aggregator) tối ưu bộ nhớ sử dụng EnumMap.
 * Tự động phân loại Flat và Percent, cung cấp hàm tính toán chuẩn:
 * result = (baseValue + flat) * (100 + percent) / 100
 */
public class StatContainer {
    private final Map<StatType, Integer> flats = new EnumMap<>(StatType.class);
    private final Map<StatType, Integer> percents = new EnumMap<>(StatType.class);

    public void add(StatModifier modifier) {
        if (modifier == null || modifier.getValue() == 0) {
            return;
        }
        if (modifier.getModType() == ModType.FLAT) {
            flats.merge(modifier.getStatType(), modifier.getValue(), Integer::sum);
        } else {
            percents.merge(modifier.getStatType(), modifier.getValue(), Integer::sum);
        }
    }

    public void add(StatType type, ModType modType, int value) {
        if (type == null || value == 0) {
            return;
        }
        if (modType == ModType.FLAT) {
            flats.merge(type, value, Integer::sum);
        } else {
            percents.merge(type, value, Integer::sum);
        }
    }

    public void addFlat(StatType type, int value) {
        if (type != null && value != 0) {
            flats.merge(type, value, Integer::sum);
        }
    }

    public void addPercent(StatType type, int value) {
        if (type != null && value != 0) {
            percents.merge(type, value, Integer::sum);
        }
    }

    public int getFlat(StatType type) {
        return flats.getOrDefault(type, 0);
    }

    public int getPercent(StatType type) {
        return percents.getOrDefault(type, 0);
    }

    /**
     * Tính toán tổng chỉ số: (Base + Flat) * (100 + Percent) / 100
     */
    public int calculate(StatType type, int baseValue) {
        int flat = getFlat(type);
        int percent = getPercent(type);
        return (int) (((long) (baseValue + flat) * (100 + percent)) / 100);
    }

    public void clear() {
        flats.clear();
        percents.clear();
    }
}
