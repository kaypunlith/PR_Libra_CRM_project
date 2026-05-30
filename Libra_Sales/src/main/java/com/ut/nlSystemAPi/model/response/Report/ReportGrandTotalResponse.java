package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportGrandTotalResponse {

    @ApiModelProperty(position = 1)
    private Long grandTotalQty;

    @ApiModelProperty(position = 2)
    private Double grandTotalAmount;

    @ApiModelProperty(position = 3)
    private Double grandTotalBalance;

    @ApiModelProperty(position = 4)
    private Long grandTotalInvoiceCode;

    @ApiModelProperty(position = 5)
    private Long grandTotalOrganization;

    @ApiModelProperty(position = 6)
    private Long grandTotalWarehouse;

    @ApiModelProperty(position = 7)
    private Long grandTotalProduct;

}
