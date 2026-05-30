package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PipelineSettingRequest {

    @ApiModelProperty(position = 1)
    private String description;

    @ApiModelProperty(position = 2)
    private List<Long> employeeGroupIds;

    @ApiModelProperty(position = 3)
    private List<PipelineSettingStageRequest> stages;
}
