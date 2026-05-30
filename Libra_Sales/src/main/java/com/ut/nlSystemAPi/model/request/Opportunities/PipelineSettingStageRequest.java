package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PipelineSettingStageRequest {

    @ApiModelProperty(position = 1)
    private Long stageId;

    @ApiModelProperty(position = 2)
    private List<Long> activityIds;

    @ApiModelProperty(position = 3)
    private Integer ordering;

    @ApiModelProperty(position = 4)
    private Double percent;

    @ApiModelProperty(position = 5)
    private Integer skippable;
}
