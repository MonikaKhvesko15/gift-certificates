package com.epam.esm.entity;

public enum UserRole {
    GUEST("GUEST"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
