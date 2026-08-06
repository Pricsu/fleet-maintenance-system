package com.fleet.fleet_maintenance_system.dto;

import com.fleet.fleet_maintenance_system.entity.User;

public class WhoAmIResponse {
    private String username;
    private String role;

    public WhoAmIResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
