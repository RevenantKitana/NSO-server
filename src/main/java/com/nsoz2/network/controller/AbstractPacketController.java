package com.nsoz2.network.controller;

import com.nsoz2.model.Char;
import com.nsoz2.model.User;
import com.nsoz2.network.Message;
import com.nsoz2.network.Service;
import com.nsoz2.network.Session;

/**
 * Base class for all domain-specific packet controllers in Server V2.
 * Provides unified session, user, character, and service access.
 */
public abstract class AbstractPacketController {

    protected final Session client;
    protected User user;
    protected Char _char;
    protected Service service;

    public AbstractPacketController(Session client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChar(Char _char) {
        this._char = _char;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public abstract void handle(int command, Message mss) throws Exception;
}

