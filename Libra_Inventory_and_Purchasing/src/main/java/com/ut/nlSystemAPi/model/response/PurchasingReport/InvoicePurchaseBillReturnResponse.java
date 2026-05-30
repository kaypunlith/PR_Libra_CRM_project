package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvoicePurchaseBillReturnResponse {
    @ApiModelProperty(position = 1)
    private String id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 5)
    private String warehouseName;

    @ApiModelProperty(position = 6)
    private String vendorName;

    @ApiModelProperty(position = 8)
    private Double subTotal;

    @ApiModelProperty(position = 9)
    private Double totalAmount;

    @ApiModelProperty(position = 10)
    private Double totalVat;

    @ApiModelProperty(position = 11)
    private Double balance;

    @ApiModelProperty(position = 12)
    private String aging;

    @ApiModelProperty(position = 13)
    private Long status;

    @ApiModelProperty(position = 14)
    private Double totalSumSubTotal;

    @ApiModelProperty(position = 15)
    private Double totalSumTotalAmount;

    @ApiModelProperty(position = 16)
    private Double totalSumTotalVat;

    @ApiModelProperty(position = 17)
    private Double totalSumBalance;

}
