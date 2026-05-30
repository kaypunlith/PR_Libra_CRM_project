package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SalesInvoiceReportDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String salesOrderDate;

    @ApiModelProperty(position = 3)
    private String salesOrderCode;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private String orderCode;

    @ApiModelProperty(position = 6)
    private Double totalAmount;

    @ApiModelProperty(position = 7)
    private Double totalVat;

    @ApiModelProperty(position = 8)
    private String aging;

    @ApiModelProperty(position = 9)
    private Integer isClose;

    @ApiModelProperty(position = 10)
    private Integer isApprove;

    @ApiModelProperty(position = 11)
    private Integer type;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private String currencySymbol;

}