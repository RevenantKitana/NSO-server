/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz.model;

/**
 * @author Administrator
 */
public class Figurehead {

    public String name;
    public short x;
    public short y;
    public int countDown;
    public long expireTime;

    // Chỉ số tích tụ và bộc phá chiêu 25 Kunai
    public Char owner;
    public int hp;
    public int maxHp;
    public int baseAttack = 50;       // Tấn công cơ bản
    public int percentAttack = 130;   // Tấn công nội công % (Opt 1)
    public int sysUp = 50;            // Tương khắc / Cường khắc (Opt 33)

    public Figurehead(String name, short x, short y, int countDown) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.countDown = countDown;
        this.expireTime = System.currentTimeMillis() + (countDown * 1000L);
    }

    public synchronized void addHp(int add) {
        this.hp += add;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
        if (this.hp <= 0) {
            this.hp = 0;
            this.expireTime = 0; // Kích nổ tức thì khi hết HP!
            this.countDown = 0;
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= this.expireTime || this.hp <= 0;
    }
}

