package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupportTicketAddMoreRequest {

    @ApiModelProperty(position = 1, notes = "SAVE, NEXT, BACK, SKIP")
    private String action;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private List<Long> activityIds;

    @ApiModelProperty(position = 4)
    private List<Long> taskIds;

    @ApiModelProperty(position = 5, notes = "Required when action = SKIP")
    private Long skipStageId;
}
