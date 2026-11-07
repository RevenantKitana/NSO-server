package com.nsoz2.model.component;

import com.nsoz2.model.Char;

/**
 * CharacterStateComponent: Quản lý sinh mệnh (HP, MP), cấp độ, điểm kinh nghiệm và trạng thái sống/chết.
 */
public class CharacterStateComponent extends AbstractComponent {

    public CharacterStateComponent(Char owner) {
        super(owner);
    }

    public int getLevel() {
        return owner.level;
    }

    public long getExp() {
        return owner.exp;
    }

    public int getHp() {
        return owner.hp;
    }

    public int getMaxHp() {
        return owner.maxHP;
    }

    public int getMp() {
        return owner.mp;
    }

    public int getMaxMp() {
        return owner.maxMP;
    }

    public boolean isDead() {
        return owner.isDead;
    }

    public void setHp(int hp) {
        owner.hp = Math.min(owner.maxHP, Math.max(0, hp));
        if (owner.hp == 0) {
            owner.isDead = true;
        }
    }

    public void setMp(int mp) {
        owner.mp = Math.min(owner.maxMP, Math.max(0, mp));
    }

    public void consumeHp(int amount) {
        setHp(owner.hp - amount);
    }

    public void consumeMp(int amount) {
        setMp(owner.mp - amount);
    }

    public void healHp(int amount) {
        setHp(owner.hp + amount);
    }

    public void healMp(int amount) {
        setMp(owner.mp + amount);
    }
}

