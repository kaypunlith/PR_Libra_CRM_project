package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseYearToDateDetailResponse {

    @ApiModelProperty(position = 1)
    private Double bills;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private Double average;

    @ApiModelProperty(position = 6)
    private Double largest;


}
