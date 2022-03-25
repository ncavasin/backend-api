package com.sip.api.domains.enums;

import lombok.Getter;

public enum UserPermission {
    ACTIVITY_READ("activity:read"),
    ACTIVITY_WRITE("activity:write");

    @Getter
    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }
}
