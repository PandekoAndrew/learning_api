package com.example.service.impl;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link UserService} provides methods to communicate with {@link UserDAO}
 *
 * @author Andrew
 * @version 1.0
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Searches all user from a storage
     *
     * @return all users or empty list
     */
    public List<User> getUsers() {
        return userDAO.findAll();
    }

    /**
     * Searches for a page of the specified size
     *
     * @param pageNum  number of page
     * @param pageSize number of users on the page
     * @return list of {@link User}
     */
    public List<User> getPage(int pageNum, int pageSize) {
        return userDAO.findPage(pageNum, pageSize);
    }

    /**
     * Searches for a user with specified
     *
     * @param username can be null
     * @return {@link User} or null
     */
    public User getUser(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * Save user at storage
     * Encrypts user password, and set up date of registration
     *
     * @param user not null. If null {@link NullPointerException} will be thrown
     */
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    /**
     * Delete user from storage
     *
     * @param username can be null
     * @return true if user successfully deleted, false if not
     */
    public boolean delete(String username) {
        return userDAO.delete(username);
    }

    /**
     * Does user have ADMIN {@link Role}
     *
     * @param user can be null.
     * @return true is user have ADMIN {@link Role}, otherwise false
     */
    public boolean isAdmin(User user) {
        User fromDAO = userDAO.findByUsername(user.getUsername());
        if (fromDAO != null) {
            boolean matches = passwordEncoder.matches(user.getPassword(), fromDAO.getPassword());
            logger.debug("passwords matches: {}", matches);
            if (matches) {
                return fromDAO.getRole() == Role.ADMIN;
            }
        }
        return false;
    }

    /**
     * Searches user at the storage
     *
     * @param username can be null
     * @return true if user exist, otherwise false
     */
    public boolean isExists(String username) {
        return (userDAO.findByUsername(username) != null);
    }

    /**
     * Updates user at the storage
     *
     * @param user can be null. If null nothing will change
     */
    public void update(User user) {
        userDAO.update(user);
    }

    public User getUpdatedUser(User user) {
        User oldUser = userDAO.findByUsername(user.getUsername());

        if (oldUser == null) {
            throw new IllegalStateException("User with your username was not found.");
        }

        if (user.getPassword() != null) {
            oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return oldUser;
    }
}
