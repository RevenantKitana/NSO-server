package com.nsoz2.model.component;

import com.nsoz2.map.zones.Zone;
import com.nsoz2.model.Char;
import com.nsoz2.util.NinjaUtils;

/**
 * SpatialComponent: Quản lý không gian, tọa độ (x, y), map, zone và khoảng cách tầm nhìn.
 */
public class SpatialComponent extends AbstractComponent {

    public SpatialComponent(Char owner) {
        super(owner);
    }

    public short getX() {
        return owner.x;
    }

    public short getY() {
        return owner.y;
    }

    public void setPosition(short x, short y) {
        owner.x = x;
        owner.y = y;
    }

    public short getMapId() {
        return owner.mapId;
    }

    public void setMapId(int mapId) {
        owner.mapId = (short) mapId;
    }

    public Zone getZone() {
        return owner.zone;
    }

    public void setZone(Zone zone) {
        owner.zone = zone;
    }

    public double getDistance(short targetX, short targetY) {
        return NinjaUtils.getDistance(owner.x, owner.y, targetX, targetY);
    }

    public boolean isInRange(short targetX, short targetY, int maxDistance) {
        return getDistance(targetX, targetY) <= maxDistance;
    }
}
