package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityAddMoreDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pipelineId;

    @ApiModelProperty(position = 3)
    private Long stageId;

    @ApiModelProperty(position = 4)
    private String stageName;

    @ApiModelProperty(position = 5)
    private Double probability;

    @ApiModelProperty(position = 6)
    private String description;
}
