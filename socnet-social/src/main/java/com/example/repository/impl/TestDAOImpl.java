package com.example.repository.impl;

import com.example.domain.Test;
import com.example.repository.TestDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDAOImpl implements TestDAO {

    private SessionFactory sessionFactory;

    TestDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Test> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Test", Test.class)
                    .list();
        }
    }

    @Override
    public List<Test> findAllByCourseId(Integer courseId) {
        try (Session session = sessionFactory.openSession()) {
            List<Test> tests = session.createQuery("FROM Test T WHERE T.course.id = :id", Test.class)
                    .setParameter("id", courseId)
                    .list();

            return tests;
        }
    }

    @Override
    public Test findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            List<Test> tests = session.createQuery("FROM Test L WHERE L.id = :id", Test.class)
                    .setParameter("id", id)
                    .list();

            if (tests.size() != 0) {
                return tests.get(0);
            }

            return null;
        }
    }

    @Override
    public void save(Test test) {
        try (Session session = sessionFactory.openSession()) {
            session.save(test);
        }
    }

    @Override
    public void update(Test test) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(test);
            tx.commit();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM Test L WHERE L.id = :id")
                    .setParameter("id", id);
            int result = query.executeUpdate();
            session.getTransaction().commit();

            if (result != 0) {
                return true;
            }

            return false;
        }
    }
}
