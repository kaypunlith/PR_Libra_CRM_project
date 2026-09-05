package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageMoveResponse {

    @ApiModelProperty(position = 1)
    private Long stageId;

    @ApiModelProperty(position = 2)
    private String stageName;

    @ApiModelProperty(position = 3)
    private Double percent;
}
