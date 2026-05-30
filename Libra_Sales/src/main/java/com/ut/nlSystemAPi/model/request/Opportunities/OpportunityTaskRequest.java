package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityTaskRequest {

    @ApiModelProperty(position = 1)
    private Long activityId;

    @ApiModelProperty(position = 2)
    private String name;
}
