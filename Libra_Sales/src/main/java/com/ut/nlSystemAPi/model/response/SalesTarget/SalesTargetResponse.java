package com.ut.nlSystemAPi.model.response.SalesTarget;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesTargetResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String targetCode;

    @ApiModelProperty(position = 2)
    private String targetName;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 5)
    private Long currencyId;

    @ApiModelProperty(position = 6)
    private String currencySymbol;

    @ApiModelProperty(position = 7)
    private Double targetAmount;

    @ApiModelProperty(position = 8)
    private Double organizationTarget;

    @ApiModelProperty(position = 5)
    private Long indicatorFirst;

    @ApiModelProperty(position = 5)
    private Long indicatorSecond;

    @ApiModelProperty(position = 5)
    private Long indicatorThird;

    @ApiModelProperty(position = 5)
    private Long employeeId;

    @ApiModelProperty(position = 5)
    private String employeeName;

    @ApiModelProperty(position = 5)
    private Long periodId;

    @ApiModelProperty(position = 5)
    private String periodName;

    @ApiModelProperty(position = 9)
    private Integer isApprove;

    @ApiModelProperty(position = 9)
    private Integer isClose;

    @ApiModelProperty(position = 10)
    private String created;

    @ApiModelProperty(position = 11)
    private String createdBy;

    @ApiModelProperty(position = 12)
    private String modified;

    @ApiModelProperty(position = 13)
    private String modifiedBy;

    @ApiModelProperty(position = 14)
    private String marketNames;

    @ApiModelProperty(position = 15)
    private List<SalesTargetMarketResponse> markets;

    @ApiModelProperty(position = 16)
    private List<Long> months;

    @ApiModelProperty(position = 17)
    private List<SalesTargetEmployeeResponse> targetEmployee;
}
