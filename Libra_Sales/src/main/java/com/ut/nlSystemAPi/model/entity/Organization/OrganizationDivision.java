package com.ut.nlSystemAPi.model.entity.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationDivision {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long organizationId;

    @ApiModelProperty(position = 3)
    private String title;

    @ApiModelProperty(position = 4)
    private String makingProcess;

    @ApiModelProperty(position = 5)
    private Long negotiationId;

}