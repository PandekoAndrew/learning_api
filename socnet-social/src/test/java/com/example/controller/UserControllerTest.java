package com.example.controller;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.TokenGenerationService;
import com.example.service.impl.UserService;
import com.example.service.impl.UserValidationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.security.SecurityConstants.HEADER_STRING;
import static com.example.security.SecurityConstants.TOKEN_PREFIX;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class UserControllerTest {
    @InjectMocks
    private UserController sut;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenGenerationService tokenGenerationService;
    @Mock
    private UserValidationService userValidationService;

    @DataProvider(name = "users")
    public static Object[][] createUsers() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.USER);

        User user2 = new User();
        user2.setUsername("Username");
        user2.setPassword("Password");
        user2.setRole(Role.USER);

        return new Object[][]{
                new Object[]{user},
                new Object[]{user2}
        };
    }

    @BeforeTest
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(userService, authenticationManager, tokenGenerationService, userValidationService);
    }

    @Test
    public void testHello() {
        assertEquals(sut.hello().getStatus(), 200);
        assertEquals(sut.hello().getEntity(), "Hello World!");
    }

    @Test(dataProvider = "users")
    public void testSignUp(User user) {
        doReturn(new HashMap<>()).when(userValidationService).validate(user);
        doReturn(false).when(userService).isExists(user.getUsername());
        doNothing().when(userService).save(user);
        doReturn("token").when(tokenGenerationService).generate();

        Response response = sut.signUp(user);

        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntity(), user);
        assertEquals(response.getHeaders(), Response.ok().header(HEADER_STRING, TOKEN_PREFIX + "token").build().getHeaders());
    }

    @Test(dataProvider = "users", expectedExceptions = {BadRequestException.class})
    public void testBadSingUp(User user) {
        doReturn(new HashMap<>()).when(userValidationService).validate(user);
        doReturn(true).when(userService).isExists(user.getUsername());
        doNothing().when(userService).save(user);
        doReturn("token").when(tokenGenerationService).generate();

        Response response = sut.signUp(user);
    }

    @Test(dataProvider = "users")
    public void testLogin(User user) {
        doReturn("token").when(tokenGenerationService).generate();

        Response response = sut.login(user.getUsername(), user.getPassword());

        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntity(), "Success!");
        assertEquals(response.getHeaders(), Response.ok().header(HEADER_STRING, TOKEN_PREFIX + "token").build().getHeaders());
    }

    @Test
    public void testGetAllUsers() {
        doReturn(new ArrayList<>()).when(userService).getUsers();

        Response response = sut.getAllUsers(0, 0);

        verify(userService).getUsers();

        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntity(), new ArrayList<>());
    }

    @Test
    public void testPaginatedGetAllUsers() {
        doReturn(new ArrayList<>()).when(userService).getPage(0, 1);

        Response response = sut.getAllUsers(0, 1);

        verify(userService).getPage(0, 1);

        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntity(), new ArrayList<>());
    }

    @Test(dataProvider = "users")
    public void testGetSpecificUser(User user) {
        doReturn(user).when(userService).getUser(user.getUsername());

        Response response = sut.getSpecificUser(user.getUsername());

        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntity(), user);
    }

    @Test(dataProvider = "users", expectedExceptions = {NotFoundException.class})
    public void testNotFoundGetSpecificUser(User user) {
        doReturn(null).when(userService).getUser(user.getUsername());

        Response response = sut.getSpecificUser(user.getUsername());
    }
}
