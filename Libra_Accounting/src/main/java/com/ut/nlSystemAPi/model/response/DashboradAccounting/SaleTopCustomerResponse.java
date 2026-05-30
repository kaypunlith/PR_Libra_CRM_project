package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleTopCustomerResponse {

    @ApiModelProperty(position = 1)
    private Long customerId;

    @ApiModelProperty(position =2)
    private String customerName;

    @ApiModelProperty(position = 3)
    private  Double totalInvoice;

    @ApiModelProperty(position = 4)
    private  Double totalAmount;


}
