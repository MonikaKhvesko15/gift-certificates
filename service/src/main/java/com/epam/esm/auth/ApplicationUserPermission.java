package com.epam.esm.auth;

public enum ApplicationUserPermission {
    CERTIFICATE_READ("certificate:read"),
    CERTIFICATE_CREATE("certificate:create"),
    CERTIFICATE_MODIFY("certificate:modify"),
    CERTIFICATE_DELETE("certificate:delete"),
    TAG_READ("tag:read"),
    TAG_CREATE("tag:create"),
    TAG_DELETE("tag:delete"),
    ORDER_READ("order:read"),
    USER_READ("user:read"),
    USER_REGISTER("user:register"),
    USER_DELETE("user:delete"),
    USER_MODIFY("user:modify"),
    USER_ORDER_READ("userOrder:read"),
    USER_ORDER_CREATE("userOrder:create"),
    USER_POPULAR_TAG("user:popularTag");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
