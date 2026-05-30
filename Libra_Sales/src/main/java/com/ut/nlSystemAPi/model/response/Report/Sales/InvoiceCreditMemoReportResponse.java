package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvoiceCreditMemoReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String cmDate;

    @ApiModelProperty(position = 3)
    private String cmNo;

    @ApiModelProperty(position = 4)
    private String invoiceCode;

    @ApiModelProperty(position = 5)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private Double subTotal;

    @ApiModelProperty(position = 7)
    private Double totalVat;

    @ApiModelProperty(position = 8)
    private Double totalAmount;

    @ApiModelProperty(position = 9)
    private Double balance;

    @ApiModelProperty(position = 10)
    private String aging;

    @ApiModelProperty(position = 11)
    private Integer status;

    @ApiModelProperty(position = 12)
    private String currencySymbol;
}