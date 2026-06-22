package com.ut.nlSystemAPi.model.response.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupportTicketAddMoreResponse {

    @ApiModelProperty(position = 1)
    private SupportTicketAddMoreDetailResponse detail;

    @ApiModelProperty(position = 2)
    private SupportTicketAddMoreStageResponse previousStage;

    @ApiModelProperty(position = 3)
    private SupportTicketAddMoreStageResponse currentStage;

    @ApiModelProperty(position = 4)
    private SupportTicketAddMoreStageResponse nextStage;

    @ApiModelProperty(position = 5)
    private List<SupportTicketAddMoreStageResponse> skipStages;

    @ApiModelProperty(position = 6)
    private List<SupportTicketAddMoreItemResponse> activities;

    @ApiModelProperty(position = 7)
    private List<SupportTicketAddMoreItemResponse> tasks;

    @ApiModelProperty(position = 8)
    private List<SupportTicketLogResponse> logs;
}
