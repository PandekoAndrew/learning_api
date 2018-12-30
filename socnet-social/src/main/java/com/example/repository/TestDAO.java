package com.example.repository;

import com.example.domain.Test;

import java.util.List;

public interface TestDAO {
    /**
     * Find all tests in datasource
     *
     * @return {@link List<Test>}
     */
    List<Test> findAll();

    /**
     * Find all tests by chapterId in datasource
     *
     * @return {@link List <Test>}
     */
    List<Test> findAllByCourseId(Integer courseId);

    /**
     * Searches test in database
     *
     * @param id can be bull
     * @return {@link Test}
     */
    Test findById(Integer id);

    /**
     * Save new test to datasource
     *
     * @param test can be null
     */
    void save(Test test);

    /**
     * Update test fields at database
     */
    void update(Test test);

    /**
     * Delete test from database
     *
     * @param id can be null
     * @return boolean
     */
    boolean delete(Integer id);
}
