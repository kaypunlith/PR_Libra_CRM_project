package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesTopBottomCustomerReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private Long totalQty;

    @ApiModelProperty(position = 3)
    private String organizationCode;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private String uomAbbr;

    @ApiModelProperty(position = 6)
    private Double totalAmount;

    @ApiModelProperty(position = 7)
    private Double avgCost;

    @ApiModelProperty(position = 8)
    private Long totalInvoice;

}