package com.quickapp.shared;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    SUPER_ADMIN, ADMIN, COSTUMER;

    @Override
    public String getAuthority() {
        return name();
    }
}