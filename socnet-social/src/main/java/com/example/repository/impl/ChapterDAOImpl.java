package com.example.repository.impl;

import com.example.domain.Chapter;
import com.example.repository.ChapterDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChapterDAOImpl implements ChapterDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public ChapterDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Chapter> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Chapter", Chapter.class)
                    .list();
        }
    }

    @Override
    public Chapter findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            List<Chapter> chapters = session.createQuery("FROM Chapter C WHERE C.id = :id", Chapter.class)
                    .setParameter("id", id)
                    .list();
            if (chapters.size() != 0) {
                return chapters.get(0);
            }
            return null;
        }
    }

    @Override
    public void save(Chapter chapter) {
        try (Session session = sessionFactory.openSession()) {
            session.save(chapter);
        }
    }

    @Override
    public void update(Chapter chapter) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(chapter);
            tx.commit();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM Chapter C WHERE C.id = :id")
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
