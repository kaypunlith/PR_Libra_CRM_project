package com.ut.nlSystemAPi.model.response.SalesTarget;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesTargetEmployeeResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 1)
    private String employeeName;

    @ApiModelProperty(position = 2)
    private Double targetOrgExisting;

    @ApiModelProperty(position = 3)
    private Double targetAmountExisting;

    @ApiModelProperty(position = 4)
    private List<SalesTargetEmployeeOrganizationResponse> organizations;

    @ApiModelProperty(position = 5)
    private Double targetAmountNew;

    @ApiModelProperty(position = 6)
    private Double targetOrgNew;
}