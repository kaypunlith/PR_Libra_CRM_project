package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePaymentByRepReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private String organizationName;

    @ApiModelProperty(position = 3)
    private String type;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String companyName;

    @ApiModelProperty(position = 6)
    private String reference;

    @ApiModelProperty(position = 7)
    private String accountName;

    @ApiModelProperty(position = 8)
    private Double amount;

    @ApiModelProperty(position = 9)
    private List<ReceivePaymentByRepReportResponse> details;
}