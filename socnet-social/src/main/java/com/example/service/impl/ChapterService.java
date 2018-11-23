package com.example.service.impl;

import com.example.domain.Chapter;
import com.example.repository.ChapterDAO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
    private final ChapterDAO chapterDAO;

    public ChapterService(ChapterDAO chapterDAO) {
        this.chapterDAO = chapterDAO;
    }

    public void create(Chapter chapter) {
        chapterDAO.save(chapter);
    }

    public Chapter getChapter(Integer id) {
        return chapterDAO.findById(id);
    }

    public void update(Chapter chapter) {
        chapterDAO.save(chapter);
    }

    public void delete(Integer id) {
        chapterDAO.delete(id);
    }

    public List<Chapter> getAllChapters() {
        return chapterDAO.findAll();
    }
}
