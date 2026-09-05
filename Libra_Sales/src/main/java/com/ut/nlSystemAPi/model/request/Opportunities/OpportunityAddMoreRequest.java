package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpportunityAddMoreRequest {

    @ApiModelProperty(position = 1, notes = "SAVE, NEXT, BACK, SKIP")
    private String action;

    @ApiModelProperty(position = 2)
    private Long quotationId;

    @ApiModelProperty(position = 3)
    private Long salesOrderId;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private List<Long> activityIds;

    @ApiModelProperty(position = 6)
    private List<Long> taskIds;

    @ApiModelProperty(position = 7, notes = "Required when action = SKIP")
    private Long skipStageId;
}
