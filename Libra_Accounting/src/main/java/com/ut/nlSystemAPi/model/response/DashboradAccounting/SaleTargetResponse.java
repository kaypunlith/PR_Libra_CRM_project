package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleTargetResponse {
    @ApiModelProperty(position = 1)
    private  Long id;

    @ApiModelProperty(position = 2)
    private String year;

    @ApiModelProperty(position = 3)
    private  Double amountQ1;

    @ApiModelProperty(position = 4)
    private Double amountQ2;

    @ApiModelProperty(position = 5)
    private  Double amountQ3;

    @ApiModelProperty(position = 6)
    private  Double amountQ4;

}
