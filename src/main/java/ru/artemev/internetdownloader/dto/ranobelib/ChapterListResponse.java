package ru.artemev.internetdownloader.dto.ranobelib;

import lombok.Data;

import java.util.List;

@Data
public class ChapterListResponse {

    private List<DataNode> data;
}
