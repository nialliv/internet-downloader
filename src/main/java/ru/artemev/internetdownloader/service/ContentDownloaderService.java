package ru.artemev.internetdownloader.service;

import ru.artemev.internetdownloader.dto.ranobelib.ChapterListResponse;
import ru.artemev.internetdownloader.dto.ranobelib.ChapterResponse;

public interface ContentDownloaderService {

    ChapterListResponse getChapters();

    ChapterResponse getChapterByBranchIdAndNumberAndVolume(Integer branchId, Integer number, Integer volume);

}
