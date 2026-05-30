package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PipelineSettingStageResponse {

    @ApiModelProperty(position = 1)
    private Long stageId;

    @ApiModelProperty(position = 2)
    private String stageName;

    @ApiModelProperty(position = 3)
    private Double percent;

    @ApiModelProperty(position = 4)
    private Integer ordering;

    @ApiModelProperty(position = 5)
    private Integer skippable;

    @ApiModelProperty(position = 6)
    private List<Long> activityIds;
}
