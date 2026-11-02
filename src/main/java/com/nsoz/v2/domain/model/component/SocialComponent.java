package com.nsoz.v2.domain.model.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 5. Social Component: Manages Clan, Party, and Social Relationships.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialComponent {
    private int clanId;
    private int partyId;

    public boolean hasClan() {
        return clanId > 0;
    }

    public boolean hasParty() {
        return partyId > 0;
    }
}

