package com.example.demo.constant;

public enum UserRole implements EnumModel {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_ADMIN");

    private final String role_user;

    UserRole(String role_user) {
        this.role_user = role_user;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return role_user;
    }
}