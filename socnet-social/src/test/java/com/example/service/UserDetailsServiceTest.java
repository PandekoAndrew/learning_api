package com.example.service;

import com.example.domain.RequestScopeStorage;
import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.UserDAO;
import com.example.service.impl.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.AfterMethod;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class UserDetailsServiceTest {
    @Mock
    UserDAO dao;

    @Mock
    RequestScopeStorage storage;

    @Mock
    private org.springframework.security.core.userdetails.User expectedUser;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(dao, storage);
    }

    @Test
    public void loadUserByUsernameTestReturnUser() {
        User user = getTestUser();
        List<Role> authorities = new ArrayList<>();
        authorities.add(user.getRole());

        when(dao.findByUsername(anyString())).thenReturn(user);
        expectedUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        assertEquals(expectedUser, userDetailsService.loadUserByUsername(anyString()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameTestReturnNull() {
        assertEquals(expectedUser, userDetailsService.loadUserByUsername(anyString()));
    }

    private User getTestUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Username");
        user.setPassword("Password");
        user.setRole(Role.USER);
        return user;
    }
}

