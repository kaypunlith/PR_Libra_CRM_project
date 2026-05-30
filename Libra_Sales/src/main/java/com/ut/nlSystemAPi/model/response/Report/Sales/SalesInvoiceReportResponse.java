package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesInvoiceReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String invoiceDate;

    @ApiModelProperty(position = 3)
    private String invoiceCode;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 4)
    private String organizationNam;

    @ApiModelProperty(position = 6)
    private Double totalAmount;

    @ApiModelProperty(position = 7)
    private Double totalVat;

    @ApiModelProperty(position = 7)
    private Double balance;

    @ApiModelProperty(position = 8)
    private String aging;

    @ApiModelProperty(position = 13)
    private String currencySymbol;

    @ApiModelProperty(position = 14)
    private String organizationGroupName;

    @ApiModelProperty(position = 15)
    private Double subTotal;

    @ApiModelProperty(position = 15)
    private Integer status;

    @ApiModelProperty(position = 16)
    private List<SalesInvoiceReportResponse> details;
}