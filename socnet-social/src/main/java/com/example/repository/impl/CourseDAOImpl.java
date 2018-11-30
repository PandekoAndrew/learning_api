package com.example.repository.impl;

import com.example.domain.Chapter;
import com.example.domain.Course;
import com.example.repository.CourseDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public CourseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Course> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Course", Course.class)
                    .list();
        }
    }

    @Override
    public List<Course> findAllByChapterId(Integer chapterId) {
        try (Session session = sessionFactory.openSession()) {
            List<Course> courses = session.createQuery("FROM Course C WHERE C.chapter.id = :id", Course.class)
                    .setParameter("id", chapterId)
                    .list();

            return courses;
        }
    }

    @Override
    public Course findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            List<Course> chapters = session.createQuery("FROM Course C WHERE C.id = :id", Course.class)
                    .setParameter("id", id)
                    .list();

            if (chapters.size() != 0) {
                return chapters.get(0);
            }

            return null;
        }
    }

    @Override
    public void save(Course course) {
        try (Session session = sessionFactory.openSession()) {
            session.save(course);
        }
    }

    @Override
    public void update(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(course);
            tx.commit();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM Course C WHERE C.id = :id")
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
