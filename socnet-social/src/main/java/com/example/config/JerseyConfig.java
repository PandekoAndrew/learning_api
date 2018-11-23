package com.example.config;

import com.example.controller.LearningController;
import com.example.controller.UserController;
import com.example.security.api.exceptionmapper.AccessDeniedExceptionMapper;
import com.example.security.api.exceptionmapper.AuthenticationExceptionMapper;
import com.example.security.api.exceptionmapper.AuthenticationTokenRefreshmentExceptionMapper;
import com.example.security.api.exceptionmapper.WebApplicationExceptionMapper;
import com.example.security.api.resource.AuthenticationResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey configuration class.
 *
 * @author cassiomolin
 */
@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    /**
     * Register Jersey resources
     */
    public JerseyConfig() {
        register(LearningController.class);
        register(UserController.class);
        register(AuthenticationResource.class);
        register(AccessDeniedExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(AuthenticationTokenRefreshmentExceptionMapper.class);
        register(WebApplicationExceptionMapper.class);
    }
}