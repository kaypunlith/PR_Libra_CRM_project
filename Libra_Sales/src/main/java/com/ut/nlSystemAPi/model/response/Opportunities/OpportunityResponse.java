package com.ut.nlSystemAPi.model.response.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Double amount;

    @ApiModelProperty(position = 5)
    private String expectedDate;

    @ApiModelProperty(position = 6)
    private Long pipelineId;

    @ApiModelProperty(position = 7)
    private String pipelineName;

    @ApiModelProperty(position = 8)
    private Long stageId;

    @ApiModelProperty(position = 9)
    private String stageName;

    @ApiModelProperty(position = 10)
    private Double probability;

    @ApiModelProperty(position = 11)
    private Long sourceId;

    @ApiModelProperty(position = 12)
    private String sourceName;

    @ApiModelProperty(position = 13)
    private Long organizationId;

    @ApiModelProperty(position = 14)
    private String organizationName;

    @ApiModelProperty(position = 13)
    private Long leadId;

    @ApiModelProperty(position = 14)
    private String leadName;

    @ApiModelProperty(position = 14)
    private String address;

    @ApiModelProperty(position = 15)
    private Long responsibilityId;

    @ApiModelProperty(position = 16)
    private String responsibilityName;

    @ApiModelProperty(position = 17)
    private Long divisionId;

    @ApiModelProperty(position = 18)
    private String divisionName;

    @ApiModelProperty(position = 19)
    private Long contactId;

    @ApiModelProperty(position = 20)
    private String contactName;

    @ApiModelProperty(position = 21)
    private Long quotationId;

    @ApiModelProperty(position = 22)
    private String quotationNo;

    @ApiModelProperty(position = 23)
    private Long salesOrderId;

    @ApiModelProperty(position = 24)
    private String salesOrderNo;

    @ApiModelProperty(position = 25)
    private String description;

    @ApiModelProperty(position = 26)
    private String created;

    @ApiModelProperty(position = 27)
    private String createdBy;

    @ApiModelProperty(position = 28)
    private String modified;

    @ApiModelProperty(position = 29)
    private String modifiedBy;

    @ApiModelProperty(position = 30)
    private Boolean isUploaded;

    @ApiModelProperty(position = 31)
    private String printStatus;

    @ApiModelProperty(position = 32)
    private Integer printCount;
}
