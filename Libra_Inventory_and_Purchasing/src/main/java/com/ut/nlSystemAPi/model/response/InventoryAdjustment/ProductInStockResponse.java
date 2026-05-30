package com.ut.nlSystemAPi.model.response.InventoryAdjustment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductInStockResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private Long locationId;

    @ApiModelProperty(position = 2)
    private String locationName;

    @ApiModelProperty(position = 3)
    private Integer isExpiredDate;

    @ApiModelProperty(position = 3)
    private String expiredDate;

    @ApiModelProperty(position = 3)
    private String sku;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String uomName;

    @ApiModelProperty(position = 6)
    private String uomAbbr;

    @ApiModelProperty(position = 7)
    private Double qtyOnHand;
}
