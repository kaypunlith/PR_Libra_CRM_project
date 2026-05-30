package com.ut.nlSystemAPi.model.response.RequestStock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestStockDetailResponse {

    @ApiModelProperty(position = 1)
    private Long requestStockId;

    @ApiModelProperty(position = 2)
    private String upc;

    @ApiModelProperty(position = 3)
    private String sku;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 5)
    private String productName;

    @ApiModelProperty(position = 6)
    private Long qty;

    @ApiModelProperty(position = 7)
    private Long qtyUomId;

    @ApiModelProperty(position = 8)
    private String uom;

    @ApiModelProperty(position = 9)
    private Long isExpiredDate;
}
