package ru.artemev.internetdownloader.service;

import java.util.List;

public interface DocxCreator {

    void saveChapter(String title, List<String> paragraphs);

}
