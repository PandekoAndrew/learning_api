package com.example.repository;

import com.example.domain.Chapter;

import java.util.List;

public interface ChapterDAO {
    /**
     * Find all chapters in datasource
     *
     * @return {@link List<Chapter>}
     */
    List<Chapter> findAll();

    /**
     * Searches chapter in database
     *
     * @param id can be bull
     * @return {@link Chapter}
     */
    Chapter findById(Integer id);

    /**
     * Save new chapter to datasource
     *
     * @param chapter can be null
     */
    void save(Chapter chapter);

    /**
     * Update chapter fields at database
     */
    void update(Chapter chapter);

    /**
     * Delete chapter from database
     *
     * @param id can be null
     * @return boolean
     */
    boolean delete(Integer id);
}
