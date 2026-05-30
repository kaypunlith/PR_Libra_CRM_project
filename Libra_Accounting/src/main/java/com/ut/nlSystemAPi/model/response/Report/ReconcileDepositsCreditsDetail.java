package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReconcileDepositsCreditsDetail {
    @ApiModelProperty(position = 1)
    private Long customerId;

    @ApiModelProperty(position = 2)
    private String customerName;

    @ApiModelProperty(position = 1)
    private String typeName;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private String reference;

    @ApiModelProperty(position = 6)
    private String clear;

    @ApiModelProperty(position = 7)
    private Double amount;

    @ApiModelProperty(position = 8)
    private Double balance;

    @ApiModelProperty(position = 9)
    private  Double totalAmount;

    @ApiModelProperty(position = 10)
    private Double totalBalance;
}
