package com.example.service;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.UserDAO;
import com.example.service.impl.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class UserServiceTest {
    @InjectMocks
    private UserService sut;
    @Mock
    private UserDAO userDAO;
    @Mock
    private PasswordEncoder passwordEncoder;

    @DataProvider(name = "users")
    public static Object[][] createUsers() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.USER);

        User user2 = new User();
        user2.setUsername("CommonUser");
        user2.setPassword("CommonPassword");
        user2.setRole(Role.USER);

        User user3 = new User();
        user3.setUsername("CommonUser");
        user3.setRole(Role.USER);

        return new Object[][]{
                new Object[]{user},
                new Object[]{user2},
                new Object[]{user3}
        };
    }

    @DataProvider(name = "admins")
    public static Object[][] createAdmins() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.ADMIN);

        User user2 = new User();
        user2.setUsername("Administrator");
        user2.setPassword("SuperPassword");
        user2.setRole(Role.ADMIN);

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
        Mockito.reset(userDAO, passwordEncoder);
    }

    @Test
    public void testGetUsers() {
        doReturn(new ArrayList<>()).when(userDAO).findAll();

        List<User> users = sut.getUsers();

        assertEquals(users, new ArrayList<>());
    }

    @Test
    public void testGetPage() {
        doReturn(new ArrayList<>()).when(userDAO).findPage(anyInt(), anyInt());

        List<User> users = sut.getPage(anyInt(), anyInt());

        assertEquals(users, new ArrayList<>());
    }

    @Test
    public void testGetUser() {
        doReturn(new User()).when(userDAO).findByUsername(anyString());

        User user = sut.getUser(anyString());

        assertEquals(user, new User());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setPassword("123456");
        doReturn("123456").when(passwordEncoder).encode(anyString());

        sut.save(user);

        verify(userDAO).save(user);
    }

    @Test
    public void testDelete() {
        doReturn(true).when(userDAO).delete(anyString());

        boolean isDeleted = sut.delete(anyString());

        assertTrue(isDeleted);
    }

    @Test(dataProvider = "admins")
    public void testValidIsAdmin(User user) {
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        doReturn(user).when(userDAO).findByUsername(anyString());

        boolean isAdmin = sut.isAdmin(user);

        assertTrue(isAdmin);
    }

    @Test(dataProvider = "users")
    public void testInvalidIsAdmin(User user) {
        doReturn(true).when(passwordEncoder).matches(anyString(), anyString());
        doReturn(user).when(userDAO).findByUsername(anyString());

        boolean isAdmin = sut.isAdmin(user);

        assertFalse(isAdmin);
    }

    @Test
    public void testValidIsExists() {
        doReturn(new User()).when(userDAO).findByUsername(anyString());

        boolean isExists = sut.isExists(anyString());

        assertTrue(isExists);
    }

    @Test
    public void testInvalidIsExists() {
        doReturn(null).when(userDAO).findByUsername(anyString());

        boolean isExists = sut.isExists(anyString());

        assertFalse(isExists);
    }

    @Test(dataProvider = "users")
    public void testUpdate(User user) {
        doNothing().when(userDAO).update(any(User.class));

        sut.update(user);

        verify(userDAO).update(user);
    }

    @Test(dataProvider = "users")
    public void testGetUpdatedUser(User user) {
        doReturn(user).when(userDAO).findByUsername(user.getUsername());
        User updatedUser = new User();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setRole(user.getRole());

        updatedUser = sut.getUpdatedUser(user);

        assertEquals(updatedUser, user);
    }

    @Test(dataProvider = "users", expectedExceptions = {IllegalStateException.class})
    public void testFailGetUpdatedUser(User user) {
        doReturn(null).when(userDAO).findByUsername(user.getUsername());

        sut.getUpdatedUser(user);
    }
}
