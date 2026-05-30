package com.ut.nlSystemAPi.model.response.PurchaseReceive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseReceiveDetailResponse {
    @ApiModelProperty(position = 1)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 1)
    private Long purchaseOrderDetailId;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 2)
    private String barcode;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private Long qtyDoc;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private String uom;

    @ApiModelProperty(position = 6)
    private Long isExpiredDate;

    @ApiModelProperty(position = 6)
    private String dateExpired;

    @ApiModelProperty(position = 6)
    private String receiveDate;

    @ApiModelProperty(position = 6)
    private String lotNumber;

    @ApiModelProperty(position = 6)
    private Long conversion;

    @ApiModelProperty(position = 6)
    private String status;

    @ApiModelProperty(position = 6)
    private Double unitCost;

    @ApiModelProperty(position = 6)
    private Double newCost;


}
