package com.example.service.impl;

import com.example.domain.Question;
import com.example.repository.QuestionDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionDAO questionDAO;

    public QuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public void create(Question question) {
        questionDAO.save(question);
    }

    public Question getQuestion(Integer id) {
        return questionDAO.findById(id);
    }

    public void update(Question question) {
        questionDAO.save(question);
    }

    public void delete(Integer id) {
        questionDAO.delete(id);
    }

    public List<Question> getAllQuestions() {
        return questionDAO.findAll();
    }

    public List<Question> getQuestionsByTestId(Integer testId) {
        return questionDAO.findAllByTestId(testId);
    }
}
