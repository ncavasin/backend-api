package com.sip.api.domains.enums;

public enum UserStatus {
    ACTIVE,     // acquired after paying
    INACTIVE,   // default
    DEACTIVATED,// logical delete
    OVERDUE     // acquired after >1 month of debt
}
