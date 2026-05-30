package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListProfitAndLostResponse {
    
    @ApiModelProperty(position = 1)
    private Long month;

    @ApiModelProperty(position = 11)
    private Double grossProfit;

    @ApiModelProperty(position = 12)
    private Double totalExpenses;

    @ApiModelProperty(position = 2)
    private Double profitLoss;

}
