package com.ut.nlSystemAPi.model.response.InventorySample;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventorySampleProductResponseDetail {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long inventorySampleProductId;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String sku;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 5)
    private Long productId;

    @ApiModelProperty(position = 6)
    private String productName;

    @ApiModelProperty(position = 7)
    private String name;

    @ApiModelProperty(position = 7)
    private String lotsNumber;

    @ApiModelProperty(position = 8)
    private Long isExpiredDate;

    @ApiModelProperty(position = 8)
    private String expiredDate;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String locationName;

    @ApiModelProperty(position = 11)
    private String uom;

    @ApiModelProperty(position = 11)
    private String uomAbbr;

    @ApiModelProperty(position = 12)
    private Long newQty;

    @ApiModelProperty(position = 13)
    private Long adjustQty;

    @ApiModelProperty(position = 14)
    private Long qtyOnHand;

    @ApiModelProperty(position = 14)
    private Double unitCost;
}
