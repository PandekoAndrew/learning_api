package com.example.service;

import com.example.service.impl.TokenGenerationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.annotations.AfterMethod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TokenGenerationServiceTest {
    @Mock
    AuthenticationTokenService authenticationTokenService;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext context;

    @InjectMocks
    private TokenGenerationServiceImpl tokenGenerationService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(authentication, authenticationTokenService, context);
    }

    @Test
    public void testGenerationServiceReturnTrue() {
        final String expectedToken = "token";
        when(authenticationTokenService.issueToken(any(), any())).thenReturn(expectedToken);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        String actualToken = tokenGenerationService.generate();

        assertEquals(expectedToken, actualToken);
    }
}
