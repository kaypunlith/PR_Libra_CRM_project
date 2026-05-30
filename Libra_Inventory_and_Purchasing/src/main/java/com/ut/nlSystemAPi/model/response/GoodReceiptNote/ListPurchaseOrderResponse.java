package com.ut.nlSystemAPi.model.response.GoodReceiptNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListPurchaseOrderResponse {
    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 1)
    private Long vendorId;

    @ApiModelProperty(position = 1)
    private Float totalDeposit;

    @ApiModelProperty(position = 1)
    private Long exchangeRateId;

    @ApiModelProperty(position = 1)
    private String prCode;

    @ApiModelProperty(position = 1)
    private String vendorName;

    @ApiModelProperty(position = 1)
    private String orderDate;

    @ApiModelProperty(position = 1)
    private Float totalAmount;

    @ApiModelProperty(position = 1)
    private Long currencyCenterId;


}
