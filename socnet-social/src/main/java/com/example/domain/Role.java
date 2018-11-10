package com.example.domain;


import org.springframework.security.core.GrantedAuthority;

/**
 * Authorities that can be granted to a {@link User}.
 *
 * @author cassiomolin
 */
public enum Role implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
