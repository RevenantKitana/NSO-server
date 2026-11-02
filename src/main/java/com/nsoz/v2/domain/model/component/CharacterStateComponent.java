package com.nsoz.v2.domain.model.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2. Character State Component: Manages Level, EXP, HP, MP, and Potential Stats.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterStateComponent {
    private int level;
    private long exp;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;

    private int potentialPoint;
    private int skillPoint;

    public void addExp(long amount) {
        if (amount > 0) {
            this.exp += amount;
        }
    }

    public void consumeHp(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    public void recoverHp(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public void consumeMp(int amount) {
        this.mp = Math.max(0, this.mp - amount);
    }

    public void recoverMp(int amount) {
        this.mp = Math.min(this.maxMp, this.mp + amount);
    }

    public boolean isDead() {
        return this.hp <= 0;
    }
}

