package com.nsoz2.model.component;

import com.nsoz2.model.Char;

/**
 * Lớp cơ sở cho toàn bộ các Component bóc tách từ Char.java.
 * Tuân thủ Strangler Fig pattern và Aggregate Root trong Domain-Driven Design.
 */
public abstract class AbstractComponent {

    protected final Char owner;

    public AbstractComponent(Char owner) {
        this.owner = owner;
    }

    public Char getOwner() {
        return owner;
    }
}
