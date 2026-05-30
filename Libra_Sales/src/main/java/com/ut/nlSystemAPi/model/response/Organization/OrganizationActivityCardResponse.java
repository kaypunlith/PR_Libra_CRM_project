package com.ut.nlSystemAPi.model.response.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationActivityCardResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long totalRecord;

    @ApiModelProperty(position = 3)
    private String latestDate;

    @ApiModelProperty(position = 5)
    private String createdBy;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 4)
    private String status;

    @ApiModelProperty(position = 4)
    private String contactName;

    @ApiModelProperty(position = 4)
    private String position;

    @ApiModelProperty(position = 4)
    private String type;

    @ApiModelProperty(position = 4)
    private String subject;

    @ApiModelProperty(position = 4)
    private String result;

    @ApiModelProperty(position = 4)
    private String other;

}