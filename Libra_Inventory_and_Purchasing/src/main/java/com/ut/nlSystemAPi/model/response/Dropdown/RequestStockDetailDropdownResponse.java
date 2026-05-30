package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestStockDetailDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private String productName;

    @ApiModelProperty(position = 4)
    private String sku;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 9)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private String uom;

    @ApiModelProperty(position = 8)
    private Long fromWarehouseId;

    @ApiModelProperty(position = 8)
    private String fromWarehouseName;

    @ApiModelProperty(position = 8)
    private Long toWarehouseId;

    @ApiModelProperty(position = 8)
    private String toWarehouseName;

    @ApiModelProperty(position = 8)
    private Long isExpiredDate;

}
