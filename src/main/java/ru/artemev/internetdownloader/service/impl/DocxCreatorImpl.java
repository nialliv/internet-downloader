package ru.artemev.internetdownloader.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${ranobes.com.path-to-save-dir}")
    private String pathToDir;

    @Override
    public void saveChapter(String title, List<String> paragraphs) {
        log.info("Started to save data in document");
        try {
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
            mainDocumentPart.addStyledParagraphOfText("Title", title);
            mainDocumentPart.addParagraphOfText(StringUtils.EMPTY);
            paragraphs
                    .forEach(paragraph -> {
                        mainDocumentPart.addParagraphOfText(paragraph);
                        mainDocumentPart.addParagraphOfText(StringUtils.EMPTY);
                    });
            String fileName;
            if (title.contains(":")) {
                fileName = "Повелитель тайн: " + title.substring(0, title.lastIndexOf(':')).strip();
            } else if (title.contains(".")) {
                fileName = "Повелитель тайн: " + title.substring(0, title.lastIndexOf('.')).strip();
            } else {
                fileName = "Повелитель тайн: " + title.substring(0, title.lastIndexOf(' ')).strip();
            }
            wordPackage.save(new File(pathToDir + fileName + ".docx"));
        } catch (Docx4JException e) {
            throw new RuntimeException(e);
        }
        log.info("Finished to save data in document");
    }
}

