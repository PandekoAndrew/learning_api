package com.example.repository.impl;

import com.example.domain.Question;
import com.example.repository.QuestionDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDAOImpl implements QuestionDAO {
    private SessionFactory sessionFactory;

    QuestionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Question> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Question", Question.class)
                    .list();
        }
    }

    @Override
    public List<Question> findAllByTestId(Integer testId) {
        try (Session session = sessionFactory.openSession()) {
            List<Question> questions = session.createQuery("FROM Question Q WHERE Q.test.id = :id", Question.class)
                    .setParameter("id", testId)
                    .list();

            return questions;
        }
    }

    @Override
    public Question findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            List<Question> questions = session.createQuery("FROM Question Q WHERE Q.id = :id", Question.class)
                    .setParameter("id", id)
                    .list();

            if (questions.size() != 0) {
                return questions.get(0);
            }

            return null;
        }
    }

    @Override
    public void save(Question question) {
        try (Session session = sessionFactory.openSession()) {
            session.save(question);
        }
    }

    @Override
    public void update(Question question) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(question);
            tx.commit();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM Question Q WHERE Q.id = :id")
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
