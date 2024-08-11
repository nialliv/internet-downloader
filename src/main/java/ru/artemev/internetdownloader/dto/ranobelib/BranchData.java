package ru.artemev.internetdownloader.dto.ranobelib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BranchData {

    @JsonProperty("branch_id")
    private Integer branchId;

}
