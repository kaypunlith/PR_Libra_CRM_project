package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseOrderDetailDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 1)
    private Long purchaseOrderDetailId;

    @ApiModelProperty(position = 2)
    private Long type;

    @ApiModelProperty(position = 3)
    private Long itemId;

    @ApiModelProperty(position = 4)
    private String itemName;

    @ApiModelProperty(position = 4)
    private String sku;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 5)
    private Long categoryId;

    @ApiModelProperty(position = 5)
    private Long isExpiredDate;

    @ApiModelProperty(position = 6)
    private String categoryName;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long qtyReceive;

    @ApiModelProperty(position = 9)
    private Long conversion;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private String uomName;

    @ApiModelProperty(position = 9)
    private Double unitPrice;

    @ApiModelProperty(position = 10)
    private Double totalPrice;

    @ApiModelProperty(position = 11)
    private String note;


    @ApiModelProperty(position = 11)
    private String expiredDate;

}
