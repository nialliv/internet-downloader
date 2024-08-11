package ru.artemev.internetdownloader.service;

import java.util.List;

public interface DocxCreator {

    void saveChapter(Integer chapterNum, String title, List<String> paragraphs);

}
