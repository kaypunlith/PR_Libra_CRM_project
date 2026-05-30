package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleTargetVsActualResponse {
    @ApiModelProperty(position = 1)
    private  String quarter;

    @ApiModelProperty(position = 2)
    private Double actualAmount;

    @ApiModelProperty(position = 1)
    private  Double targetAmount;

    @ApiModelProperty(position = 2)
    private Double average;

    @ApiModelProperty(position = 1)
    private  Double grandActualAmount;

    @ApiModelProperty(position = 2)
    private Double grandTargetAmount;

    @ApiModelProperty(position = 1)
    private  Double AverageGrandActualAmount;

    @ApiModelProperty(position = 2)
    private Double AverageGrandTargetAmount;

}
