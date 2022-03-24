package com.sip.api.domain.user;

public enum UserStatus {
    ACTIVE,     // acquired after paying
    INACTIVE,   // default
    DELETED,    // logical delete
    OVERDUE     // acquired after >1 month of debt
}
