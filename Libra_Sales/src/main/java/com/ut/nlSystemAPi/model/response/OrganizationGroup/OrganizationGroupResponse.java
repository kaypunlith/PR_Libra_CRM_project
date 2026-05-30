package com.ut.nlSystemAPi.model.response.OrganizationGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationGroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String companies;

    @ApiModelProperty(position = 4)
    private String employeeGroups;

    @ApiModelProperty(position = 5)
    private String priceTypes;

    @ApiModelProperty(position = 5)
    private String modified;

    @ApiModelProperty(position = 5)
    private String modifiedBy;

    @ApiModelProperty(position = 6)
    private List<OrganizationGroupDetailResponse> company;

    @ApiModelProperty(position = 7)
    private List<OrganizationGroupDetailResponse> employeeGroup;

    @ApiModelProperty(position = 8)
    private List<OrganizationGroupDetailResponse> priceType;

    @ApiModelProperty(position = 9)
    private List<OrganizationGroupDetailResponse> organization;
}