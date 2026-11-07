package com.nsoz2.model.component;

import com.nsoz2.combat.CombatResult;
import com.nsoz2.combat.CombatService;
import com.nsoz2.mob.Mob;
import com.nsoz2.model.Char;
import com.nsoz2.skill.Skill;
import java.util.Vector;

/**
 * CombatComponent: Quản lý kỹ năng chiến đấu, chỉ số công/thủ/kháng và kích hoạt Damage Engine đối xứng.
 */
public class CombatComponent extends AbstractComponent {

    public CombatComponent(Char owner) {
        super(owner);
    }

    public Vector<Skill> getSkills() {
        return owner.vSkill;
    }

    public Skill getSelectedSkill() {
        return owner.selectedSkill;
    }

    public void setSelectedSkill(Skill skill) {
        owner.selectedSkill = skill;
    }

    public int getDamage() {
        return owner.damage;
    }

    public int getDamage2() {
        return owner.damage2;
    }

    public int getFatal() {
        return owner.fatal;
    }

    public int getExactly() {
        return owner.exactly;
    }

    public int getMiss() {
        return owner.miss;
    }

    public byte getTypePk() {
        return owner.typePk;
    }

    public void setTypePk(byte typePk) {
        owner.setTypePk(typePk);
    }

    public CombatResult calculatePvP(Char target, boolean isFatal) {
        return CombatService.calculatePvPDamage(owner, target, isFatal);
    }

    public int calculatePvE(Mob mob, boolean isFatal, int add) {
        return CombatService.calculatePvEDamage(owner, mob, isFatal, add);
    }
}