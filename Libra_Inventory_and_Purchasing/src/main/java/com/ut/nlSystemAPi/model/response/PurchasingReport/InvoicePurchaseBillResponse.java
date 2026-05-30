package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class InvoicePurchaseBillResponse {

    @ApiModelProperty(position = 1)
    private String id;

    @ApiModelProperty(position = 2)
    private String orderDate;

    @ApiModelProperty(position = 3)
    private String poCode;

    @ApiModelProperty(position = 4)
    private String locationName;

    @ApiModelProperty(position = 5)
    private String vendorName;

    @ApiModelProperty(position = 6)
    private Double subTotal;

    @ApiModelProperty(position = 7)
    private Double totalAmount;

    @ApiModelProperty(position = 8)
    private Double totalVat;

    @ApiModelProperty(position = 9)
    private Double balance;

    @ApiModelProperty(position = 10)
    private String aging;

    @ApiModelProperty(position = 11)
    private Long status;

    @ApiModelProperty(position = 12)
    private Double totalSumSubTotal;

    @ApiModelProperty(position = 13)
    private Double totalSumTotalAmount;

    @ApiModelProperty(position = 14)
    private Double totalSumTotalVat;

    @ApiModelProperty(position = 15)
    private Double totalSumBalance;

}
