package com.example.service.impl;

import com.example.domain.Test;
import com.example.repository.TestDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestDAO testDAO;

    public TestService(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    public void create(Test test) {
        testDAO.save(test);
    }

    public Test getTest(Integer id) {
        return testDAO.findById(id);
    }

    public void update(Test test) {
        testDAO.save(test);
    }

    public void delete(Integer id) {
        testDAO.delete(id);
    }

    public List<Test> getAllTests() {
        return testDAO.findAll();
    }

    public List<Test> getTestsByCourseId(Integer courseId) {
        return testDAO.findAllByCourseId(courseId);
    }
}
