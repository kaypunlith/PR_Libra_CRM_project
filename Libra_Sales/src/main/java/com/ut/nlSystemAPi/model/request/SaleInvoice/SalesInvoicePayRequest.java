package com.ut.nlSystemAPi.model.request.SaleInvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesInvoicePayRequest {

    @ApiModelProperty(position = 1)
    private Long saleInvoiceId;

    @ApiModelProperty(position = 3)
    private Long exchangeRateId;

    @ApiModelProperty(position = 4)
    private Long currencyCenterId;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private Double paidUsd;

    @ApiModelProperty(position = 5)
    private Double paidOther;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private Double balance;

    @ApiModelProperty(position = 5)
    private Double balanceOther;

    @ApiModelProperty(position = 5)
    private Double change;

    @ApiModelProperty(position = 5)
    private Double changeOther;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 7)
    private String aging;

}
