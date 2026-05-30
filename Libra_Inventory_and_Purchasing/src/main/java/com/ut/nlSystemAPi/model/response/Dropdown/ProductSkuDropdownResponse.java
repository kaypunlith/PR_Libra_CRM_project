package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductSkuDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 3)
    private String upc;

    @ApiModelProperty(position = 4)
    private String sku;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String uomName;

    @ApiModelProperty(position = 6)
    private String uomAbbr;

    @ApiModelProperty(position = 7)
    private Double unitPrice;

    @ApiModelProperty(position = 7)
    private Double unitCost;

    @ApiModelProperty(position = 8)
    private Double estimateCost;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private String expiryDate;

    @ApiModelProperty(position = 12)
    private Long fromLocationId;

    @ApiModelProperty(position = 13)
    private Long toLocationId;

    @ApiModelProperty(position = 14)
    private String fromLocationName;

    @ApiModelProperty(position = 15)
    private String toLocationName;

    @ApiModelProperty(position = 16)
    private Long qtyInStock;

    @ApiModelProperty(position = 16)
    private Long qty;

    @ApiModelProperty(position = 17)
    private Long isExpiredDate;

    @ApiModelProperty(position = 18)
    private Long conversion;

}
