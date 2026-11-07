/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz2.option;

import com.nsoz2.server.GameData;
import com.nsoz2.skill.SkillOptionTemplate;

public class SkillOption {

    public int param;
    public SkillOptionTemplate optionTemplate;
    public SkillOption(int templateId, int param) {
        this.param = param;
        this.optionTemplate = GameData.getInstance().getOptionTemplates().get(templateId);
    }
}
