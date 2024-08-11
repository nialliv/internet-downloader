package ru.artemev.internetdownloader.dto.ranobelib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataNodeV2 {

    private Integer volume;

    private Integer number;

    private String name;

    @JsonProperty("branch_id")
    private Integer branchId;

    private ContentNode content;
}
