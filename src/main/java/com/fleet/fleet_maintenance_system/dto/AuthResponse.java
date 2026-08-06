package com.fleet.fleet_maintenance_system.dto;

public class AuthResponse {

    private String username;
    private String token;
    private String role;

    public AuthResponse(String username, String token, String role) {
        this.username = username;
        this.token = token;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
