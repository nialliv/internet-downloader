package ru.artemev.internetdownloader.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.artemev.internetdownloader.service.ContentDownloaderService;

import java.io.IOException;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContentDownloaderServiceImpl implements ContentDownloaderService {

    private static final Map<String, String> HEADERS = Map.of(
            "Postman-Token", "980d593a-bc47-412a-83f8-a3579435dd86"
    );

    private static final Map<String, String> COOCKIES = Map.of(
            "PHPSESSID", "tm7om9rj9vat2ppfh23qcv0v3h",
            "chp_179", "chapters%2Flord-of-the-mysteries%2F276290-glava-651-povtornoe-zasedanie.html"
    );

    @Value("${ranobes.com.max-page}")
    private Integer numMaxPage;

    @Value("${ranobes.com.url-with-pages-chapters}")
    private String urlWithPagesChapters;

    @Override
    public Map<String, String> getMapChapters() {
        Map<String, String> map = new HashMap<>();
        for (int page = 1; page <= numMaxPage; page++) {
            log.info("Page - [{}] in process", page);
            Optional.ofNullable(getDocumentPage(page))
                    .map(this::parseToEntry)
                    .ifPresent(map::putAll);
        }
        return map;
    }

    @Override
    public List<String> downloadParagraphs(String url) {
        List<String> paragraphs = new ArrayList<>();
        Document chapterHtml = getResponse(url);
        List<Node> arrticle = chapterHtml.getElementById("arrticle").childNodes();
        arrticle.forEach(node -> {
            if (!node.nameIs("br")
                    && !node.nameIs("div")) {
                paragraphs.add(node.outerHtml().strip());
            }
        });
        return paragraphs;
    }

    private Map<String, String> parseToEntry(Document document) {
        Map<String, String> titlesOnPage = new HashMap<>();
        Elements titlesUrls = document.getElementsByClass("cat_block cat_line");
        titlesUrls.forEach(titleElement -> {
            Attributes aTag = titleElement.getElementsByTag("a").getFirst().attributes();
            titlesOnPage.put(aTag.get("title"), aTag.get("href"));
        });
        log.info("Added chapters - [{}]", titlesOnPage.size());
        return titlesOnPage;
    }

    private Document getDocumentPage(int page) {
        return getResponse(urlWithPagesChapters + page);
    }

    private Document getResponse(String url) {
        try {
            return Jsoup.connect(url)
                    .followRedirects(true)
                    .userAgent("PostmanRuntime/7.41.2")
                    .cookies(COOCKIES)
                    .headers(HEADERS)
                    .get();
        } catch (IOException e) {
            log.error("Cannot get document from url - [{}]", url, e);
            return null;
        }
    }

//    private static final String BRANCH_ID = "branch_id";
//    private static final String NUMBER = "number";
//    private static final String VOLUME = "volume";
//
//    @Value("${lord-of-the-mysteries.rest.chapters}")
//    private String chaptersUri;
//    @Value("${lord-of-the-mysteries.rest.chapter}")
//    private String oneChapter;
//
//    @Override
//    public ChapterListResponse getChapters() {
//        return RestClient.create().get()
//                .uri(chaptersUri)
//                .retrieve()
//                .body(ChapterListResponse.class);
//    }
//
//    @Override
//    public ChapterResponse getChapterByBranchIdAndNumberAndVolume(Integer branchId, Integer number, Integer volume) {
//        String uri = UriComponentsBuilder.fromUriString(oneChapter)
//                .queryParam(BRANCH_ID, branchId)
//                .queryParam(NUMBER, number)
//                .queryParam(VOLUME, volume)
//                .encode()
//                .toUriString();
//            return RestClient.create()
//                    .get()
//                    .uri(uri)
//                    .retrieve()
//                    .body(ChapterResponse.class);
////            return RestClient.create()
////                    .get()
////                    .uri(uri)
////                    .retrieve()
////                    .body(ChapterResponseV2.class);
//
//    }
}
