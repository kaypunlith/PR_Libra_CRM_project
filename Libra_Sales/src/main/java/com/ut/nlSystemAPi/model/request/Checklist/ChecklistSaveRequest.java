package com.ut.nlSystemAPi.model.request.Checklist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChecklistSaveRequest {

    @ApiModelProperty(position = 1)
    private List<Long> activityIds;

    @ApiModelProperty(position = 2)
    private List<Long> taskIds;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Map<String, Object> checkedData;
}
