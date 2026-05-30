package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceByRepReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationGroupId;

    @ApiModelProperty(position = 2)
    private String organizationGroupName;

    @ApiModelProperty(position = 7)
    private String orderDate;

    @ApiModelProperty(position = 5)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private String invoiceCode;

    @ApiModelProperty(position = 7)
    private String memo;

    @ApiModelProperty(position = 7)
    private String aging;

    @ApiModelProperty(position = 8)
    private Double totalAmount;

    @ApiModelProperty(position = 8)
    private Double balance;

    @ApiModelProperty(position = 8)
    private Integer status;

    @ApiModelProperty(position = 9)
    private List<InvoiceByRepReportResponse> details;
}