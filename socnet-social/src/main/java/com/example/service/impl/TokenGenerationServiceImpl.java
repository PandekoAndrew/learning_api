package com.example.service.impl;

import com.example.domain.Role;
import com.example.service.AuthenticationTokenService;
import com.example.service.TokenGenerationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TokenGenerationService}.
 *
 * @author Roman
 * {@inheritDoc}
 */
@Service
public class TokenGenerationServiceImpl implements TokenGenerationService {

    private final AuthenticationTokenService authenticationTokenService;

    public TokenGenerationServiceImpl(AuthenticationTokenService authenticationTokenService) {
        this.authenticationTokenService = authenticationTokenService;
    }

    /**
     * Get username and authorities from {@link SecurityContextHolder}.
     *
     * @return token if username and authorities not empty
     */
    @Override
    public String generate() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<Role> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.toString()))
                .collect(Collectors.toSet());
        return authenticationTokenService.issueToken(username, authorities);
    }
}
