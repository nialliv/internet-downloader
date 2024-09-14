package ru.artemev.internetdownloader.service;

import java.util.List;
import java.util.Map;

public interface ContentDownloaderService {

    Map<String, String> getMapChapters();

    List<String> downloadParagraphs(String url);

}
