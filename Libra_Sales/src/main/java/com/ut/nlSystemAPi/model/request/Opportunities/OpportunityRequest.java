package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String expectedDate;

    @ApiModelProperty(position = 3)
    private Long pipelineId;

    @ApiModelProperty(position = 4)
    private Long stageId;

    @ApiModelProperty(position = 5)
    private Long sourceId;

    @ApiModelProperty(position = 6)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private Long employeeId;

    @ApiModelProperty(position = 8)
    private Long vendorId;

    @ApiModelProperty(position = 9)
    private Double amount;

    @ApiModelProperty(position = 10)
    private Long responsibilityId;

    @ApiModelProperty(position = 11)
    private Double probability;

    @ApiModelProperty(position = 12)
    private Long divisionId;

    @ApiModelProperty(position = 13)
    private Long contactId;

    @ApiModelProperty(position = 14)
    private Long quotationId;

    @ApiModelProperty(position = 15)
    private Long salesOrderId;

    @ApiModelProperty(position = 16)
    private String description;
}
