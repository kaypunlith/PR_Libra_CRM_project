package com.ut.nlSystemAPi.model.request.OrganizationGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationGroupRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private List<Long> company;

    @ApiModelProperty(position = 4)
    private List<Long> employeeGroup;

    @ApiModelProperty(position = 5)
    private List<Long> priceType;

    @ApiModelProperty(position = 6)
    private List<Long> organization;

}