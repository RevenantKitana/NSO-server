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

    // Chỉ số tích tụ và bộc phá chiêu 25 Kunai
    public Char owner;
    public int hitsTaken = 0;
    public int baseAttack = 50;       // Tấn công cơ bản (Opt 0)
    public int iceAttack = 150;       // Băng sát (Opt 3)
    public int percentAttack = 100;   // Tấn công nội công % (Opt 11)
    public int bonusPercentPerHit = 5;// % cộng thêm mỗi hit

    public Figurehead(String name, short x, short y, int countDown) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.countDown = countDown;
    }
}
