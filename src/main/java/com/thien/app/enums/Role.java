package com.thien.app.enums;

public enum Role {
    ADMIN(1, "admin"),
    CUSTOMER(2, "customer");

    private final int value;
    private final String name;

    Role(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}