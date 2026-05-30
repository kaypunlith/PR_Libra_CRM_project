package com.ut.nlSystemAPi.model.request.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationActivityCardRequest {

    @ApiModelProperty(position = 2)
    private Long organizationContactId;

    @ApiModelProperty(position = 2)
    private String position;

    @ApiModelProperty(position = 2)
    private Long actionStatusId;

    @ApiModelProperty(position = 2)
    private String issueDate;

    @ApiModelProperty(position = 2)
    private String subject;

    @ApiModelProperty(position = 2)
    private String resultAction;

    @ApiModelProperty(position = 2)
    private String other;

}