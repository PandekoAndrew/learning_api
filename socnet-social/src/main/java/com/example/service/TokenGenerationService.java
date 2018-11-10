package com.example.service;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Used for generate authentication token
 */
public interface TokenGenerationService {
    /**
     * Generates token, using {@link SecurityContextHolder}
     *
     * @return token
     */
    String generate();
}
