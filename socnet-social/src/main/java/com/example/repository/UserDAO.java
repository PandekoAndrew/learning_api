package com.example.repository;

import com.example.domain.User;

import java.util.List;

/**
 * Produce methods to work with datasource
 *
 * @author Andrew
 * @version 1.0
 */
public interface UserDAO {

    /**
     * Find all users in datasource
     *
     * @return {@link List<User>}
     */
    List<User> findAll();

    /**
     * Searches user in database
     *
     * @param username can be bull
     * @return {@link User}
     */
    User findByUsername(String username);

    /**
     * Save new user to datasource
     *
     * @param user can be null
     */
    void save(User user);

    /**
     * Searches page with fixed size
     *
     * @param pageNum @param pageSize
     * @return pageSize users or less, if quantity of users less in database
     */
    List<User> findPage(int pageNum, int pageSize);

    /**
     * Delete user from database
     *
     * @param username can be null
     * @return boolean
     */
    boolean delete(String username);

    /**
     * Update user fields at database
     */
    void update(User user);
}
