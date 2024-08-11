package ru.artemev.internetdownloader.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.artemev.internetdownloader.dto.ranobelib.ChapterListResponse;
import ru.artemev.internetdownloader.dto.ranobelib.ChapterResponse;
import ru.artemev.internetdownloader.service.ContentDownloaderService;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContentDownloaderServiceImpl implements ContentDownloaderService {

    private static final String BRANCH_ID = "branch_id";
    private static final String NUMBER = "number";
    private static final String VOLUME = "volume";

    @Value("${lord-of-the-mysteries.rest.chapters}")
    private String chaptersUri;
    @Value("${lord-of-the-mysteries.rest.chapter}")
    private String oneChapter;

    @Override
    public ChapterListResponse getChapters() {
        return RestClient.create().get()
                .uri(chaptersUri)
                .retrieve()
                .body(ChapterListResponse.class);
    }

    @Override
    public ChapterResponse getChapterByBranchIdAndNumberAndVolume(Integer branchId, Integer number, Integer volume) {
        String uri = UriComponentsBuilder.fromUriString(oneChapter)
                .queryParam(BRANCH_ID, branchId)
                .queryParam(NUMBER, number)
                .queryParam(VOLUME, volume)
                .encode()
                .toUriString();
            return RestClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(ChapterResponse.class);
//            return RestClient.create()
//                    .get()
//                    .uri(uri)
//                    .retrieve()
//                    .body(ChapterResponseV2.class);

    }
}
