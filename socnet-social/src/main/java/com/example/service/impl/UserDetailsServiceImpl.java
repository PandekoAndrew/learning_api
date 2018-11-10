package com.example.service.impl;

import com.example.domain.RequestScopeStorage;
import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link UserDetailsService}. Using for get {@link UserDetails} from database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserDAO userDao;
    private final RequestScopeStorage storage;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDao, RequestScopeStorage storage) {
        this.userDao = userDao;
        this.storage = storage;
    }

    /**
     * Load user from database by username
     *
     * @param username can be null
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException if username doesn't exist in database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<Role> authorities = new ArrayList<>();
        authorities.add(user.getRole());
        storage.setCurrentUserId(user.getId());
        logger.info("current user id : {}", storage.getCurrentUserId());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
