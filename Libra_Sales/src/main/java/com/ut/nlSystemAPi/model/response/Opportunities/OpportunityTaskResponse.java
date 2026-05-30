package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityTaskResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long activityId;

    @ApiModelProperty(position = 3)
    private String activityName;

    @ApiModelProperty(position = 4)
    private String taskName;

    @ApiModelProperty(position = 5)
    private String created;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String modified;

    @ApiModelProperty(position = 8)
    private String modifiedBy;
}
