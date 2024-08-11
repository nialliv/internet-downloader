package ru.artemev.internetdownloader.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.artemev.internetdownloader.dto.ranobelib.ChapterListResponse;
import ru.artemev.internetdownloader.dto.ranobelib.ChapterResponse;
import ru.artemev.internetdownloader.dto.ranobelib.DataNode;
import ru.artemev.internetdownloader.service.BookSaverService;
import ru.artemev.internetdownloader.service.ContentDownloaderService;
import ru.artemev.internetdownloader.service.DocxCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSaverServiceImpl implements BookSaverService {

    @Value("${lord-of-the-mysteries.translate-branch-id:18695}")
    private Integer translateBranchId;

    @Value("${lord-of-the-mysteries.errors}")
    private String errorsChapters;

    private static final AtomicInteger saves = new AtomicInteger();

    private final ContentDownloaderService contentDownloaderService;
    private final DocxCreator docxCreator;

    @Override
    public void downloadLordOfTheMysteries() {
        log.info("=== Start download chapters ===");
        ChapterListResponse chapters = contentDownloaderService.getChapters();
        log.info("Got [{}] chapters", chapters.getData().size());
//        todo realize in other method
//        List<Integer> errChapters = Arrays.stream(errorsChapters.split(","))
//                .map(String::trim)
//                .map(Integer::parseInt)
//                .toList();
//        List<DataNode> filtered = chapters.getData()
//                .stream()
//                .filter(dataNode -> errChapters.contains(dataNode.getNumber()))
//                .toList();
        asyncDownloadAndSave(chapters.getData());
    }

    @SneakyThrows
    private void asyncDownloadAndSave(List<DataNode> chapters) {
        List<Integer> chaptersWithErrors = new ArrayList<>();
        for (DataNode dataNode : chapters) {
            try {
                ChapterResponse chapter = getChapter(dataNode);
                docxCreator.saveChapter(chapter.getData().getNumber(), chapter.getData().getName(), getListParagraphs(chapter.getData().getContent()));
                saves.getAndIncrement();
            } catch (Exception e) {
                log.error("Error with chapter - [{}]", dataNode.getNumber(), e);
                chaptersWithErrors.add(dataNode.getNumber());
            }
            Thread.sleep(500);
        }
        log.info("=== Finish: saves - [{}], errors - [{}] ===", saves, chaptersWithErrors);
    }

    private List<String> getListParagraphs(String content) {
        List<String> paragraphs = new ArrayList<>();
        Document document = Jsoup.parse(content);
        document.getElementsByTag("p")
                .stream()
                .map(Element::text)
                .forEach(paragraphs::add);
        return paragraphs;
    }

    private ChapterResponse getChapter(DataNode dataNode) {
        return contentDownloaderService.getChapterByBranchIdAndNumberAndVolume(
                translateBranchId,
                dataNode.getNumber(),
                dataNode.getVolume()
        );
    }

}
