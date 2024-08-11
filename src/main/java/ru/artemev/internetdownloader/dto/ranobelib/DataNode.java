package ru.artemev.internetdownloader.dto.ranobelib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DataNode {

    private ModelType model;

    private Integer number;

    private Integer volume;

    private String name;

    @JsonProperty("branch_id")
    private String branchId;

    @JsonProperty("branches_count")
    private Integer branchesCount;

    private List<BranchData> branches;

    private String content;
}
