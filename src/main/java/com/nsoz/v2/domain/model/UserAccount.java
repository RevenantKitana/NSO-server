package com.nsoz.v2.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Pure Domain Entity representing a User Account.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    private int id;
    private String username;
    private String password;
    private int role;       // 0: Normal, 99: Admin
    private int status;     // 1: Active, 0: Banned
    private int luong;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public boolean isAdmin() {
        return role >= 99;
    }

    public boolean isBanned() {
        return status <= 0;
    }
}

