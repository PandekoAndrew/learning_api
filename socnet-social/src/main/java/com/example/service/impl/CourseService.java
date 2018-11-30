package com.example.service.impl;

import com.example.domain.Course;
import com.example.repository.CourseDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseDAO courseDAO;

    public CourseService(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void create(Course course) {
        courseDAO.save(course);
    }

    public Course getCourse(Integer id) {
        return courseDAO.findById(id);
    }

    public void update(Course course) {
        courseDAO.save(course);
    }

    public void delete(Integer id) {
        courseDAO.delete(id);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }
    
    public List<Course> getCoursesByChapterId(Integer chapterId){
        return courseDAO.findAllByChapterId(chapterId);
    }
}
