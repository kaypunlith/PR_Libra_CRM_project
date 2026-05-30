package com.ut.nlSystemAPi.model.request.SalesTarget;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesTargetEmployeeRequest {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private Double organizationTargetAmountExisting;

    @ApiModelProperty(position = 3)
    private Double targetAmountExisting;

    @ApiModelProperty(position = 4)
    private List<Long> organizations;

    @ApiModelProperty(position = 5)
    private Double targetAmountNew;

    @ApiModelProperty(position = 6)
    private Double organizationTargetAmountNew;
}