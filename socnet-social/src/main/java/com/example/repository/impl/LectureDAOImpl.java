package com.example.repository.impl;

import com.example.domain.Lecture;
import com.example.repository.LectureDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LectureDAOImpl implements LectureDAO {

    private SessionFactory sessionFactory;

    public LectureDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Lecture> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Lecture", Lecture.class)
                    .list();
        }
    }

    @Override
    public List<Lecture> findAllByCourseId(Integer chapterId) {
        try (Session session = sessionFactory.openSession()) {
            List<Lecture> lectures = session.createQuery("FROM Lecture L WHERE L.course.id = :id", Lecture.class)
                    .setParameter("id", chapterId)
                    .list();

            return lectures;
        }
    }

    @Override
    public Lecture findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            List<Lecture> lectures = session.createQuery("FROM Lecture L WHERE L.id = :id", Lecture.class)
                    .setParameter("id", id)
                    .list();

            if (lectures.size() != 0) {
                return lectures.get(0);
            }

            return null;
        }
    }

    @Override
    public void save(Lecture lecture) {
        try (Session session = sessionFactory.openSession()) {
            session.save(lecture);
        }
    }

    @Override
    public void update(Lecture lecture) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(lecture);
            tx.commit();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM Lecture L WHERE L.id = :id")
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
