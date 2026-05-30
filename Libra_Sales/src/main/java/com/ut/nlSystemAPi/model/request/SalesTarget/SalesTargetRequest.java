package com.ut.nlSystemAPi.model.request.SalesTarget;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesTargetRequest {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String targetName;

    @ApiModelProperty(position = 4)
    private Long currencyId;

    @ApiModelProperty(position = 5)
    private Double targetAmount;

    @ApiModelProperty(position = 6)
    private Long indicatorFirst;

    @ApiModelProperty(position = 7)
    private Long indicatorSecond;

    @ApiModelProperty(position = 8)
    private Long indicatorThird;

    @ApiModelProperty(position = 9)
    private Double organizationTarget;

    @ApiModelProperty(position = 10)
    private Long periodId;

    @ApiModelProperty(position = 11)
    private Long employeeId;

    @ApiModelProperty(position = 12)
    private List<Long> months;

    @ApiModelProperty(position = 13)
    private List<Long> marketIds;

    @ApiModelProperty(position = 14)
    private List<SalesTargetEmployeeRequest> employeeTarget;
}
