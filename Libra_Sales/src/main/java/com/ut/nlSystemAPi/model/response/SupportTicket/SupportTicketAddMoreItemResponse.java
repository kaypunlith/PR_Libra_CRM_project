package com.ut.nlSystemAPi.model.response.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicketAddMoreItemResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long activityId;

    @ApiModelProperty(position = 4)
    private String activityName;

    @ApiModelProperty(position = 5)
    private Boolean selected;
}
