/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsoz.effect;

import lombok.Getter;
import org.json.simple.JSONObject;

@Getter
public class Effect {

    public final byte EFF_ME = 0;
    public final byte EFF_FRIEND = 1;

    public int param;
    public int param2;
    public EffectTemplate template;
    private long startAt;
    private long endAt;
    public int tickCount = 0;
    public int maxTicks = -1;

    public Effect(int templateId, long startAt, long endAt, int param) {
        this.template = EffectTemplateManager.getInstance().find(templateId);
        this.startAt = startAt;
        this.endAt = endAt;
        this.param = param;
        if (this.template != null && (this.template.type == 4 || this.template.type == 17) && (endAt - startAt) <= 30000) {
            this.maxTicks = (int) ((endAt - startAt) / 500);
        }
    }

    public Effect(int templateId, long length, int param) {
        this.template = EffectTemplateManager.getInstance().find(templateId);
        this.startAt = System.currentTimeMillis();
        this.endAt = startAt + length;
        this.param = param;
        if (this.template != null && (this.template.type == 4 || this.template.type == 17) && length <= 30000) {
            this.maxTicks = (int) (length / 500);
        }
    }

    public boolean isExpired() {
        if (maxTicks > 0) {
            return tickCount >= maxTicks || System.currentTimeMillis() >= endAt + 3000L;
        }
        return System.currentTimeMillis() >= endAt;
    }

    public int getTimeStart() {
        return (int) ((System.currentTimeMillis() - startAt) / 1000);
    }

    public int getTimeLength() {
        return (int) (endAt - startAt);
    }


    public long getTimeRemaining() {

        return endAt - System.currentTimeMillis();
    }

    public void addTime(long time) {
        this.endAt += time;
    }

    public JSONObject toJSONObject() {
        JSONObject job = new JSONObject();
        job.put("id", this.template.id);
        job.put("start_at", startAt);
        job.put("end_at", endAt);
        job.put("param", this.param);
        job.put("time_length", endAt-System.currentTimeMillis());
        return job;
    }
}
