package com.example.repository;

import com.example.domain.Lecture;

import java.util.List;

public interface LectureDAO {
    /**
     * Find all lectures in datasource
     *
     * @return {@link List<Lecture>}
     */
    List<Lecture> findAll();

    /**
     * Find all lectures by chapterId in datasource
     *
     * @return {@link List<Lecture>}
     */
    List<Lecture> findAllByCourseId(Integer chapterId);

    /**
     * Searches lecture in database
     *
     * @param id can be bull
     * @return {@link Lecture}
     */
    Lecture findById(Integer id);

    /**
     * Save new lecture to datasource
     *
     * @param lecture can be null
     */
    void save(Lecture lecture);

    /**
     * Update lecture fields at database
     */
    void update(Lecture lecture);

    /**
     * Delete lecture from database
     *
     * @param id can be null
     * @return boolean
     */
    boolean delete(Integer id);
}
