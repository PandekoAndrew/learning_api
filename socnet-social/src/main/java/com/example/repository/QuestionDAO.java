package com.example.repository;

import com.example.domain.Question;

import java.util.List;

public interface QuestionDAO {
    /**
     * Find all questions in datasource
     *
     * @return {@link List<Question>}
     */
    List<Question> findAll();

    /**
     * Find all questions by chapterId in datasource
     *
     * @return {@link List <Question>}
     */
    List<Question> findAllByTestId(Integer testId);

    /**
     * Searches question in database
     *
     * @param id can be bull
     * @return {@link Question}
     */
    Question findById(Integer id);

    /**
     * Save new question to datasource
     *
     * @param question can be null
     */
    void save(Question question);

    /**
     * Update question fields at database
     */
    void update(Question question);

    /**
     * Delete question from database
     *
     * @param id can be null
     * @return boolean
     */
    boolean delete(Integer id);
}
