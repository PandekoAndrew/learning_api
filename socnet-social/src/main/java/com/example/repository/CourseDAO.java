package com.example.repository;

import com.example.domain.Course;

import java.util.List;

public interface CourseDAO {
    /**
     * Find all courses in datasource
     *
     * @return {@link List<Course>}
     */
    List<Course> findAll();

    /**
     * Find all courses by chapterId in datasource
     *
     * @return {@link List<Course>}
     */
    List<Course> findAllByChapterId(Integer chapterId);

    /**
     * Searches course in database
     *
     * @param id can be bull
     * @return {@link Course}
     */
    Course findById(Integer id);

    /**
     * Save new course to datasource
     *
     * @param course can be null
     */
    void save(Course course);

    /**
     * Update course fields at database
     */
    void update(Course course);

    /**
     * Delete course from database
     *
     * @param id can be null
     * @return boolean
     */
    boolean delete(Integer id);
}
