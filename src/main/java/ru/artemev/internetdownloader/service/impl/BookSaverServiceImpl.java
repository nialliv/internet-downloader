package ru.artemev.internetdownloader.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.artemev.internetdownloader.service.BookSaverService;
import ru.artemev.internetdownloader.service.ContentDownloaderService;
import ru.artemev.internetdownloader.service.DocxCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookSaverServiceImpl implements BookSaverService {

    private final ContentDownloaderService contentDownloaderService;
    private final DocxCreator docxCreator;

    @SneakyThrows
    @Override
    public void downloadLordOfTheMysteries() {
        log.info("=== Start download chapters ===");
        List<String> errorsWithChapters = new ArrayList<>();
        //number chapter rus string -> url
        Map<String, String> chapters = contentDownloaderService.getMapChapters();
        log.info("Got [{}] chapters", chapters.size());
        chapters.forEach((title, url) -> {
            log.info("Start download chapter [{}]", title);

            try {
                List<String> paragraphs = contentDownloaderService.downloadParagraphs(url);
                docxCreator.saveChapter(title, paragraphs);
                Thread.sleep(500);
            } catch (Exception ex) {
                log.error("Cannot download chapter - [{}]", title, ex);
                errorsWithChapters.add(title);
            }

            log.info("Finish download chapter [{}]", title);
        });
        log.info("Errors with chapters [{}]", errorsWithChapters);
    }


//    @SneakyThrows
//    private void asyncDownloadAndSave(List<DataNode> chapters) {
//        List<Integer> chaptersWithErrors = new ArrayList<>();
//        for (DataNode dataNode : chapters) {
//            try {
//                ChapterResponse chapter = getChapter(dataNode);
//                docxCreator.saveChapter(chapter.getData().getNumber(), chapter.getData().getName(), getListParagraphs(chapter.getData().getContent()));
//                saves.getAndIncrement();
//            } catch (Exception e) {
//                log.error("Error with chapter - [{}]", dataNode.getNumber(), e);
//                chaptersWithErrors.add(dataNode.getNumber());
//            }
//            Thread.sleep(500);
//        }
//        log.info("=== Finish: saves - [{}], errors - [{}] ===", saves, chaptersWithErrors);
//    }
//
//    private List<String> getListParagraphs(String content) {
//        List<String> paragraphs = new ArrayList<>();
//        Document document = Jsoup.parse(content);
//        document.getElementsByTag("p")
//                .stream()
//                .map(Element::text)
//                .forEach(paragraphs::add);
//        return paragraphs;
//    }
//
//    private ChapterResponse getChapter(DataNode dataNode) {
//        return contentDownloaderService.getChapterByBranchIdAndNumberAndVolume(
//                translateBranchId,
//                dataNode.getNumber(),
//                dataNode.getVolume()
//        );
//    }

}
