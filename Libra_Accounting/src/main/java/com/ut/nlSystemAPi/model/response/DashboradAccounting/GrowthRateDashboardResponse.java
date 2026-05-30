package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GrowthRateDashboardResponse {
    @ApiModelProperty(position = 1)
    private String month;

    @ApiModelProperty(position = 2)
    private Double totalThisYear;

    @ApiModelProperty(position = 2)
    private Double totalLastYear;

    @ApiModelProperty(position = 2)
    private Double growthRate;

    @ApiModelProperty(position = 2)
    private Double grandTotalThisYear;

    @ApiModelProperty(position = 2)
    private Double grandTotalLastYear;

    @ApiModelProperty(position = 2)
    private Double grandGrowthRate;


}
