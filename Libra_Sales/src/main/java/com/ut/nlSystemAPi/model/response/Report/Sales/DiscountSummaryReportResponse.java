package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiscountSummaryReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 5)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private String invoiceCode;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private Double totalAmount;
}