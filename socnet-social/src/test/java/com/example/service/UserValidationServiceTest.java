package com.example.service;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.service.impl.UserValidationService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class UserValidationServiceTest {
    private UserValidationService sut = new UserValidationService();

    @DataProvider(name = "users")
    public static Object[][] createData() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.USER);

        User user2 = new User();
        user2.setUsername("Administrator");
        user2.setPassword("SuperPassword");
        user2.setRole(Role.ADMIN);

        return new Object[][]{
                new Object[]{user},
                new Object[]{user2}
        };
    }

    @DataProvider(name = "badUsers")
    public static Object[][] createBadData() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("pass");
        user.setRole(Role.USER);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword("password");
        user2.setRole(Role.USER);

        User user3 = new User();
        user3.setUsername("username");
        user3.setPassword("password");
        user3.setRole(Role.USER);

        User user4 = new User();
        user4.setUsername("username");
        user4.setPassword("password");
        user4.setRole(Role.USER);

        return new Object[][]{
                new Object[]{user},
                new Object[]{user2},
                new Object[]{user3},
                new Object[]{user4}
        };
    }

    @BeforeTest
    public void init() {
        sut.setYear(1950);
    }

    @Test(dataProvider = "users")
    public void testValidUserReturnsNoErrors(User user) {
        Map<String, String> errors = sut.validate(user);

        assertEquals(new HashMap<>(), errors);
    }

    @Test(dataProvider = "badUsers")
    public void testInvalidUserReturnsErrors(User user) {
        Map<String, String> errors = sut.validate(user);

        assertNotEquals(new HashMap<>(), errors);
    }
}
