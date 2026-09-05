package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTaskRequest {

    @ApiModelProperty(position = 1)
    private Long activityId;

    @ApiModelProperty(position = 2)
    private String name;
}
