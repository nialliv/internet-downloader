package ru.artemev.internetdownloader.dto.ranobelib;

import lombok.Data;

import java.util.List;

@Data
public class ContentNode {

    private String type;

    private List<ContentNode> content;
}
