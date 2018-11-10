package com.example.repository.impl;

import com.example.domain.User;
import com.example.repository.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of {@link UserDAO} interface
 * Produce methods to work with database
 *
 * @author Andrew
 * @version 1.0
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return list of users or empty list
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class)
                    .list();
        }
    }

    /**
     * @inheritDoc Calculate start position and select pageSize users
     */
    @Override
    public List<User> findPage(int pageNum, int pageSize) {
        int start = pageNum * pageSize;
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            query.setMaxResults(pageSize);
            query.setFirstResult(start);
            return query.list();
        }
    }

    /**
     * If username exist searches user in database
     *
     * @param username can be null
     * @return {@link User} or null if user isn't exist
     */
    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("FROM User U WHERE U.username = :username", User.class)
                    .setParameter("username", username)
                    .list();
            if (users.size() != 0) {
                return users.get(0);
            }
            return null;
        }
    }

    /**
     * Save new user to database
     *
     * @param user can be null
     * @throws SQLException if user is null
     */
    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
    }

    /**
     * @param username can be null
     * @return true if user successfully deleted, false if not
     * {@inheritDoc}
     */
    @Override
    public boolean delete(String username) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM User WHERE username = :username")
                    .setParameter("username", username);
            int result = query.executeUpdate();
            session.getTransaction().commit();

            if (result != 0) {
                return true;
            }
            return false;
        }
    }

    /**
     * Update user fields at database
     *
     * @param user can be null
     *             throws SQLException if user is null
     */
    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        }
    }
}
