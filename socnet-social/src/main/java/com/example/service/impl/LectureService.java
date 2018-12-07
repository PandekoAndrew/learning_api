package com.example.service.impl;

import com.example.domain.Lecture;
import com.example.repository.LectureDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {
    private final LectureDAO lectureDAO;

    public LectureService(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    public void create(Lecture lecture) {
        lectureDAO.save(lecture);
    }

    public Lecture getLecture(Integer id) {
        return lectureDAO.findById(id);
    }

    public void update(Lecture lecture) {
        lectureDAO.save(lecture);
    }

    public void delete(Integer id) {
        lectureDAO.delete(id);
    }

    public List<Lecture> getAllLectures() {
        return lectureDAO.findAll();
    }

    public List<Lecture> getLecturesByCourseId(Integer chapterId) {
        return lectureDAO.findAllByCourseId(chapterId);
    }
}
