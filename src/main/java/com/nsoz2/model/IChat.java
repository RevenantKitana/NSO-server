/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz2.model;

import com.nsoz2.network.Message;

/**
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public interface IChat {

    public void read(Message ms);

    public void wordFilter();

    public void send();
}
