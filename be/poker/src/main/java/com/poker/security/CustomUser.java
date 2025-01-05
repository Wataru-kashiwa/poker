package com.poker.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private final Long userId;

    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities,
                      Long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
