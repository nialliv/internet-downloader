package ru.artemev.internetdownloader.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.artemev.internetdownloader.service.DocxCreator;

import java.io.File;
import java.util.List;


@Slf4j
@Service
public class DocxCreatorImpl implements DocxCreator {

    @Value("${lord-of-the-mysteries.path-to-dir}")
    private String pathToDir;

    @Override
    public void saveChapter(Integer chapterNum, String title, List<String> paragraphs) {
        log.info("Started to save data in document");
        try {
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            mainDocumentPart.addStyledParagraphOfText("Title", title);
            mainDocumentPart.addParagraphOfText("");
            paragraphs
                    .forEach(mainDocumentPart::addParagraphOfText);
            wordPackage.save(new File(pathToDir + chapterNum + " - " + title + ".docx"));
        } catch (Docx4JException e) {
            throw new RuntimeException(e);
        }
        log.info("Finished to save data in document");
    }
}

