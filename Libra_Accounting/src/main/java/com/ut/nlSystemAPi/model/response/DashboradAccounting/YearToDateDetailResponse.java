package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YearToDateDetailResponse {
    
    @ApiModelProperty(position = 1)
    private Long month;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

    @ApiModelProperty(position = 1)
    private  Double largestAmount;

    @ApiModelProperty(position = 2)
    private Double totalInvoice;

    @ApiModelProperty(position = 5)
    private Double average;

    @ApiModelProperty(position = 6)
    private Double largest;

}
