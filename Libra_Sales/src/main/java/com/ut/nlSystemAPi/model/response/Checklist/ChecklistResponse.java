package com.ut.nlSystemAPi.model.response.Checklist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChecklistResponse {

    @ApiModelProperty(position = 1)
    private Long pipelineId;

    @ApiModelProperty(position = 2)
    private Long stageId;

    @ApiModelProperty(position = 3)
    private String stageName;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private List<ChecklistItemResponse> activities;

    @ApiModelProperty(position = 6)
    private List<ChecklistItemResponse> tasks;

    @ApiModelProperty(position = 7)
    private Map<String, Object> checkedData;
}
