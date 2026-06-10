package com.ut.nlSystemAPi.model.request.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicketRequest {

    @ApiModelProperty(position = 1)
    private String caseTitle;

    @ApiModelProperty(position = 2)
    private String summary;

    @ApiModelProperty(position = 3)
    private Long typeId;

    @ApiModelProperty(position = 4)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private Long priorityId;

    @ApiModelProperty(position = 6)
    private Long assignedTo;

    @ApiModelProperty(position = 7)
    private Long customerContactId;

    @ApiModelProperty(position = 8)
    private Long employeeGroupId;

    @ApiModelProperty(position = 9)
    private Long pipelineId;

    @ApiModelProperty(position = 10)
    private Long stageId;
}
