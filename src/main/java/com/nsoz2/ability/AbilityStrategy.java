/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz2.ability;

import com.nsoz2.model.Char;

/**
 * @author Admin
 */
public interface AbilityStrategy {

    public abstract void setAbility(Char owner);
}
