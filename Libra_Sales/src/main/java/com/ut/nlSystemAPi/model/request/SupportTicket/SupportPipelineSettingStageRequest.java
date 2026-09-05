package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupportPipelineSettingStageRequest {

    @ApiModelProperty(position = 1)
    private Long stageId;

    @ApiModelProperty(position = 2)
    private Double percent;

    @ApiModelProperty(position = 3)
    private Integer ordering;

    @ApiModelProperty(position = 4)
    private Integer skippable;

    @ApiModelProperty(position = 5)
    private List<Long> activityIds;
}
