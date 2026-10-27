/*
 * To change owner license header, choose License Headers in Project Properties.
 * To change owner template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz.ability;

import com.nsoz.combat.engine.NewDamageEngine;
import com.nsoz.item.Equip;
import com.nsoz.item.Item;
import com.nsoz.item.ItemManager;
import com.nsoz.item.Mount;
import com.nsoz.model.Char;
import com.nsoz.model.stat.OptionMapper;
import com.nsoz.model.stat.OptionStandardizer;
import com.nsoz.model.stat.StatContainer;
import com.nsoz.model.stat.StatType;
import com.nsoz.option.ItemOption;
import com.nsoz.option.SkillOption;
import com.nsoz.server.GameData;
import com.nsoz.skill.Skill;

/**
 * Tái cấu trúc hệ thống tính toán chỉ số nhân vật (Ability From Equip).
 * - Sử dụng StatContainer & OptionMapper để gom chỉ số theo kiến trúc hiện đại.
 * - Sửa triệt để bug Option 12 (Kháng Băng bị cộng vào Chính Xác).
 * - Sửa triệt để bug nhân đôi basicMp và công thức % MP (Option 28, 60).
 * - Duy trì 100% tính tương thích ngược với mảng owner.options, owner.optionsSupportSkill.
 */
public class AbilityFromEquip implements AbilityStrategy {

    @Override
    public void setAbility(Char owner) {
        // 1. Khởi tạo mảng tương thích ngược
        int optionLength = ItemManager.getInstance().getOptionSize();
        owner.options = new int[optionLength];
        owner.haveOptions = new boolean[optionLength];

        int templateLength = GameData.getInstance().getOptionTemplates().size();
        owner.optionsSupportSkill = new int[templateLength];

        // 2. Thu thập chỉ số trang bị vào StatContainer
        StatContainer stats = new StatContainer();
        collectEquipmentOptions(owner, stats);

        // 3. Thu thập chỉ số từ kỹ năng (chủ động, bị động, chiến đấu)
        int[] skillOptions = new int[templateLength];
        int[] skillOptions2 = new int[templateLength];
        collectSkillOptions(owner, skillOptions, skillOptions2);

        // 4. Tính toán điểm tiềm năng cơ sở
        int potentialBonus = stats.getFlat(StatType.POTENTIAL); // Opt 57
        int potentialPercent = stats.getPercent(StatType.POTENTIAL); // Opt 58

        // 5. Tính toán Sát thương (damage & damage2)
        calculateDamage(owner, stats, skillOptions, skillOptions2, potentialBonus, potentialPercent);

        // 6. Tính toán Sinh lực (Max HP)
        calculateMaxHP(owner, stats, potentialBonus, potentialPercent);

        // 7. Tính toán Nội lực (Max MP - Đã sửa lỗi dấu cộng)
        calculateMaxMP(owner, stats, potentialBonus, potentialPercent);

        // 8. Tính Né đòn & Chính xác (Đã gỡ lỗi Opt 12 Kháng Băng khỏi Chính xác)
        calculateAccuracyAndEvasion(owner, stats, potentialBonus, potentialPercent);

        // 9. Tính Kháng 3 hệ (Đã gom tự động Opt 36, 118 Kháng tất cả các hệ)
        calculateResistances(owner, stats);

        // 10. Tính các chỉ số phòng thủ, phản đòn, chí mạng, tốc độ
        calculateCombatUtilities(owner, stats);
    }

    /**
     * Gom toàn bộ option từ Bijuu, Trang bị, Thời trang, Thú cưỡi, Mặt nạ.
     */
    private void collectEquipmentOptions(Char owner, StatContainer stats) {
        // Bijuu
        if (owner.bijuu[4] != null && (!owner.bijuu[4].isExpired())) {
            for (Item bijuu : owner.bijuu) {
                if (bijuu != null) {
                    for (ItemOption itemOption : bijuu.getOptions()) {
                        addOption(owner, stats, itemOption.optionTemplate.id, itemOption.param);
                    }
                }
            }
        }

        // Trang bị chính
        for (Equip equip : owner.equipment) {
            if (equip != null && !equip.isExpired()) {
                for (ItemOption o : equip.getOptions()) {
                    if (isOptionActive(equip, o)) {
                        addOption(owner, stats, o.optionTemplate.id, o.param);
                    }
                }
            }
        }

        // Thời trang
        for (Equip equip : owner.fashion) {
            if (equip != null && !equip.isExpired()) {
                for (ItemOption o : equip.getOptions()) {
                    if (isOptionActive(equip, o)) {
                        addOption(owner, stats, o.optionTemplate.id, o.param);
                    }
                }
            }
        }

        // Thú cưỡi
        if (owner.mount[4] != null && (!owner.mount[4].isExpired())) {
            for (Mount mount : owner.mount) {
                if (mount != null) {
                    for (ItemOption o : mount.getOptions()) {
                        addOption(owner, stats, o.optionTemplate.id, o.param);
                    }
                }
            }
        }

        // Mặt nạ
        Item mask = owner.getMask();
        if (mask != null) {
            for (ItemOption o : mask.getOptions()) {
                addOption(owner, stats, o.optionTemplate.id, o.param);
            }
        }
    }

    /**
     * Kiểm tra điều kiện mở dòng ẩn theo cấp cường hóa trang bị.
     */
    private boolean isOptionActive(Equip equip, ItemOption o) {
        // Mở khóa toàn bộ chỉ số kích ẩn và kích cấp độ (mọi dòng luôn có hiệu lực 100%)
        return true;
    }

    /**
     * Nạp đồng thời vào mảng legacy options và StatContainer theo chuẩn hóa Canonical.
     */
    private void addOption(Char owner, StatContainer stats, int optionId, int param) {
        int canonicalId = OptionStandardizer.getCanonicalId(optionId);
        if (canonicalId >= 0 && canonicalId < owner.options.length) {
            owner.options[canonicalId] += param;
            owner.haveOptions[canonicalId] = true;
        }
        OptionMapper.apply(stats, canonicalId, param, owner.getSideClass());
    }

    /**
     * Gom option từ kỹ năng đang chọn, kỹ năng hỗ trợ và kỹ năng chiến đấu.
     */
    private void collectSkillOptions(Char owner, int[] skillOptions, int[] skillOptions2) {
        Skill selectedSkill = owner.selectedSkill;
        if (selectedSkill != null) {
            for (SkillOption option : selectedSkill.options) {
                skillOptions[option.optionTemplate.id] += option.param;
            }
        }

        for (Skill skill : owner.vSupportSkill) {
            for (SkillOption option : skill.options) {
                owner.optionsSupportSkill[option.optionTemplate.id] += option.param;
            }
        }

        for (Skill skill : owner.vSkillFight) {
            if (skill.template.id == Skill.SKILL_CLICK_USE_ATTACK) {
                for (SkillOption option : skill.options) {
                    skillOptions2[option.optionTemplate.id] += option.param;
                }
            }
        }
    }

    /**
     * Tính toán Sát thương đầu ra theo mô hình chuẩn hóa (Simplified Damage Engine).
     * Đặc tả: TAI_LIEU_THIET_KE_CONG_THUC_SAT_THUONG.md
     * - Tinh giản bộ chỉ số: Tách biệt Tầng Tính Toán (Logic) và Tầng Hiển Thị (UI).
     * - Quy tụ về 3 biến số trang bị (TC, VC, STC) + 1 biến số nhân vật (TN).
     * - Công thức 2 cụm: Cụm 1 (Lực đánh cơ sở) * Cụm 2 (Hệ số chống lạm phát).
     */
    private void calculateDamage(Char owner, StatContainer stats, int[] skillOptions, int[] skillOptions2,
                                int potentialBonus, int potentialPercent) {
        boolean isPhysical = (owner.getSideClass() == 0);

        // 1. Tiềm năng hiệu dụng (TN): Sức khỏe cho phái Ngoại, Chakra cho phái Nội
        int potentialIndex = isPhysical ? 0 : 3;
        int potentialDame = (owner.potential[potentialIndex] + potentialBonus)
                + (owner.potential[potentialIndex] * potentialPercent / 100);

        // 2. Tổng điểm tấn công cố định (TC - Flat Attack duy nhất: Opt 0)
        int totalTC = stats.getFlat(StatType.ATTACK);

        // Tấn công chiêu thức đang chọn (skillOptions 2..10)
        for (int i = 2; i <= 10; i++) {
            totalTC += skillOptions[i];
        }

        // Hỗ trợ nguyên tố từ kỹ năng bị động (optionsSupportSkill 5..10)
        for (int i = 5; i <= 10; i++) {
            totalTC += owner.optionsSupportSkill[i];
        }

        // Sát thương theo hệ nhân vật (Opt 88, 89, 90)
        switch (owner.getSys()) {
            case 1:
                totalTC += stats.getFlat(StatType.ELEMENT_FIRE_ATTACK);
                break;
            case 2:
                totalTC += stats.getFlat(StatType.ELEMENT_ICE_ATTACK);
                break;
            case 3:
                totalTC += stats.getFlat(StatType.ELEMENT_WIND_ATTACK);
                break;
        }

        // 3. Tổng phần trăm vận công (% Scaling - VC duy nhất: Opt 8)
        int totalVC = stats.getPercent(StatType.ATTACK);
        totalVC += isPhysical ? owner.optionsSupportSkill[0] : owner.optionsSupportSkill[1];
        totalVC += skillOptions[11]; // Tấn công cơ bản từ chiêu thức: +#%

        // Bùa buff / Dược phẩm tạm thời
        if (owner.incrDame > 0) {
            totalVC += owner.incrDame;
        }
        if (!isPhysical && owner.incrDame2 > 0) {
            totalVC += owner.incrDame2;
        }
        if (owner.incrDame3 > 0) {
            totalVC += owner.incrDame3;
        }

        // Hỗ trợ chiêu thức 3x, 4x, 5x
        if (owner.selectedSkill != null && !owner.selectedSkill.template.skills.isEmpty()) {
            int level = owner.selectedSkill.template.skills.get(0).level;
            if (level == 30) {
                totalVC += skillOptions2[58];
            } else if (level == 40) {
                totalVC += skillOptions2[59];
            } else if (level == 50) {
                totalVC += skillOptions2[60];
            }
        }

        // 4. Hằng số đặc trưng môn phái (T)
        double classT = NewDamageEngine.getClassMultiplier(owner.classId);

        // Basic attack lưu trữ giá trị cơ sở (tương thích hệ thống)
        owner.basicAttack = (int) Math.round(potentialDame * classT + totalTC);

        // 5. Tính toán Sát thương hệ đầu ra qua NewDamageEngine (K = 25.0)
        owner.damage = NewDamageEngine.calculateElementalDamage(owner.level, totalTC, potentialDame, classT, totalVC);

        // 6. Min Dame = 90% Max Dame
        owner.damage2 = owner.damage - owner.damage / 10;
    }

    /**
     * Tính toán Máu tối đa (Max HP).
     */
    private void calculateMaxHP(Char owner, StatContainer stats, int potentialBonus, int potentialPercent) {
        int effTheLuc = (owner.potential[2] + potentialBonus) + (owner.potential[2] * potentialPercent / 100);
        int basicHp = effTheLuc * 10;

        int hpPercent = stats.getPercent(StatType.MAX_HP) + owner.optionsSupportSkill[17];
        owner.maxHP = basicHp + (basicHp * hpPercent / 100) + stats.getFlat(StatType.MAX_HP);

        // Flat HP từ kỹ năng bị động (Option 72)
        if (owner.optionsSupportSkill.length > 72) {
            owner.maxHP += owner.optionsSupportSkill[72];
        }

        // % HP sau khi mặc đồ (Option 128)
        if (stats.getPercent(StatType.HP_AFTER_EQUIP) > 0) {
            owner.maxHP += (owner.maxHP * stats.getPercent(StatType.HP_AFTER_EQUIP) / 100);
        }

        owner.maxHP += owner.incrHP;
        if (owner.maxHP <= 0) {
            owner.maxHP = 50;
        }
        if (owner.hp > owner.maxHP) {
            owner.hp = owner.maxHP;
        }
    }

    /**
     * Tính toán Mana tối đa (Max MP).
     * Cân bằng chỉ số chống lạm phát MP:
     * - Phái nội công (Tiêu, Cung, Quạt): 1 Chakra = 5 MP cơ bản
     * - Phái ngoại công (Kiếm, Kunai, Đao, Tân thủ): 1 Chakra = 8 MP cơ bản
     */
    private void calculateMaxMP(Char owner, StatContainer stats, int potentialBonus, int potentialPercent) {
        int effChakra = (owner.potential[3] + potentialBonus) + (owner.potential[3] * potentialPercent / 100);
        int mpPerPoint = (owner.getSideClass() == 1) ? 5 : 8;
        int basicMp = effChakra * mpPerPoint;

        int mpPercent = stats.getPercent(StatType.MAX_MP) + owner.optionsSupportSkill[18];
        owner.maxMP = basicMp + (basicMp * mpPercent / 100) + stats.getFlat(StatType.MAX_MP);

        // Flat MP từ kỹ năng bị động (Option 73)
        if (owner.optionsSupportSkill.length > 73) {
            owner.maxMP += owner.optionsSupportSkill[73];
        }

        if (owner.maxMP <= 0) {
            owner.maxMP = 50;
        }
        if (owner.mp > owner.maxMP) {
            owner.mp = owner.maxMP;
        }
    }

    /**
     * Tính toán Điểm chính xác và Né đòn.
     * Sửa dứt điểm lỗi Option 12 (Kháng Băng) bị cộng nhầm vào Điểm Chính Xác.
     */
    private void calculateAccuracyAndEvasion(Char owner, StatContainer stats, int potentialBonus, int potentialPercent) {
        int effThanPhap = (owner.potential[1] + potentialBonus) + (owner.potential[1] * potentialPercent / 100);

        // Chính xác = Thân pháp + Flat Accuracy + Skill bị động + Buff tạm thời
        owner.exactly = effThanPhap + stats.getFlat(StatType.ACCURACY)
                + owner.optionsSupportSkill[12] + owner.incrExactly;

        // Né đòn = Thân pháp * 150% + Flat Evasion + Skill bị động + Buff tạm thời
        owner.miss = (effThanPhap * 150 / 100) + stats.getFlat(StatType.EVASION)
                + owner.optionsSupportSkill[13] + owner.incrMiss;
    }

    /**
     * Tính toán Kháng 3 hệ (Hỏa, Băng, Phong).
     * Đã gom tự động Option 36 & 118 (Kháng tất cả các hệ) vào cả 3 hệ.
     * Option 12 (Kháng Băng) được tính chuẩn xác vào resIce tại đây.
     */
    private void calculateResistances(Char owner, StatContainer stats) {
        int resAll = stats.getFlat(StatType.RES_ALL);

        owner.resFire = stats.getFlat(StatType.RES_FIRE) + resAll
                + owner.optionsSupportSkill[19] + owner.optionsSupportSkill[20]
                + owner.incrRes1 + owner.incrRes2;

        owner.resIce = stats.getFlat(StatType.RES_ICE) + resAll
                + owner.optionsSupportSkill[19] + owner.optionsSupportSkill[21]
                + owner.incrRes1 + owner.incrRes2;

        owner.resWind = stats.getFlat(StatType.RES_WIND) + resAll
                + owner.optionsSupportSkill[19] + owner.optionsSupportSkill[22]
                + owner.incrRes1 + owner.incrRes2;
    }

    /**
     * Tính toán các chỉ số bổ trợ chiến đấu: DameDown, Chí mạng, Phản đòn, Tốc độ.
     */
    private void calculateCombatUtilities(Char owner, StatContainer stats) {
        // Giảm trừ sát thương cố định (dameDown - Opt 47, 74, 80, 124)
        owner.dameDown = stats.getFlat(StatType.DAMAGE_REDUCTION);

        // Chí mạng
        owner.fatal = stats.getFlat(StatType.CRIT_RATE) + owner.optionsSupportSkill[14];
        owner.fatalDame = stats.getFlat(StatType.CRIT_DAMAGE); // Opt 105
        owner.percentFatalDame = stats.getPercent(StatType.CRIT_DAMAGE); // Opt 39, 67

        // Phản đòn cận chiến (Opt 15, 91, 126)
        owner.reactDame = stats.getFlat(StatType.REFLECT_MELEE) + owner.optionsSupportSkill[15];

        // Hệ thống khắc hệ
        owner.sysDown = owner.optionsSupportSkill[32];
        owner.sysUp = owner.optionsSupportSkill[33];

        // Tốc độ di chuyển
        owner.speed = 5;
        if (owner.mount[4] != null) {
            owner.speed += 2;
        }
        owner.speed += stats.getFlat(StatType.MOVE_SPEED);
        // Tốc độ di chuyển từ kỹ năng bị động (Option 16: mỗi 10 điểm = +1 tốc chạy)
        if (owner.optionsSupportSkill.length > 16) {
            owner.speed += owner.optionsSupportSkill[16] / 10;
        }
    }
}
