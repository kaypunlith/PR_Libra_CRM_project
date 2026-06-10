package com.ut.nlSystemAPi.model.response.SupportTicket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SupportTicketResponse {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private String code;
    @ApiModelProperty(position = 3)
    private String caseTitle;
    @ApiModelProperty(position = 4)
    private String summary;
    @ApiModelProperty(position = 5)
    private Long pipelineId;
    @ApiModelProperty(position = 6)
    private String pipelineName;
    @ApiModelProperty(position = 7)
    private Long stageId;
    @ApiModelProperty(position = 8)
    private String stageName;
    @ApiModelProperty(position = 9)
    private Double probability;
    @ApiModelProperty(position = 10)
    private Long typeId;
    @ApiModelProperty(position = 11)
    private String typeName;
    @ApiModelProperty(position = 12)
    private Long organizationId;
    @ApiModelProperty(position = 13)
    private String organizationName;
    @ApiModelProperty(position = 14)
    private Long priorityId;
    @ApiModelProperty(position = 15)
    private String priorityName;
    @ApiModelProperty(position = 16)
    private Long assignedTo;
    @ApiModelProperty(position = 17)
    private String assignedToName;
    @ApiModelProperty(position = 18)
    private Long contactId;
    @ApiModelProperty(position = 19)
    private String contactName;
    @ApiModelProperty(position = 20)
    private String created;
    @ApiModelProperty(position = 21)
    private String createdBy;
    @ApiModelProperty(position = 22)
    private String modified;
    @ApiModelProperty(position = 23)
    private String modifiedBy;
}
