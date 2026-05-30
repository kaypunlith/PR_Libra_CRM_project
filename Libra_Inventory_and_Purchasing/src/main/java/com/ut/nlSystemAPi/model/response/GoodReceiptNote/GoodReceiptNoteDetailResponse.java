package com.ut.nlSystemAPi.model.response.GoodReceiptNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodReceiptNoteDetailResponse {

    @ApiModelProperty(position = 1)
    private String upc;

    @ApiModelProperty(position = 2)
    private String sku;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 4)
    private Long purchaseOrderDetailId;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Long isExpiredDate;

    @ApiModelProperty(position = 5)
    private String expDate;

    @ApiModelProperty(position = 6)
    private Long qty;

    @ApiModelProperty(position = 6)
    private Long qtyReceive;

    @ApiModelProperty(position = 7)
    private String uom;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private Double unitCost;

    @ApiModelProperty(position = 8)
    private Long conversion;

    @ApiModelProperty(position = 9)
    private String lotNo;
}
