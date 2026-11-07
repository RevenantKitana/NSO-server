package com.nsoz2.model.component;

import com.nsoz2.clan.Clan;
import com.nsoz2.clan.Member;
import com.nsoz2.model.Char;
import com.nsoz2.party.Group;

/**
 * SocialComponent: Quản lý mối quan hệ xã hội, tổ đội (Party), gia tộc (Clan), bạn bè và thù oán.
 */
public class SocialComponent extends AbstractComponent {

    public SocialComponent(Char owner) {
        super(owner);
    }

    public Group getParty() {
        return owner.getGroup();
    }

    public void setParty(Group group) {
        owner.setGroup(group);
    }

    public Clan getClan() {
        return owner.clan;
    }

    public Member getClanMember() {
        if (owner.clan != null) {
            return owner.clan.getMemberByName(owner.name);
        }
        return null;
    }

    public boolean isInParty() {
        return owner.getGroup() != null;
    }

    public boolean isInClan() {
        return owner.clan != null;
    }

    public void leaveParty() {
        if (owner.getGroup() != null) {
            owner.outParty();
        }
    }

    public void leaveClan() {
        if (owner.clan != null) {
            owner.outClan();
        }
    }
}
