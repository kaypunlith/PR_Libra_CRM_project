package com.ut.nlSystemAPi.model.request.Login.RequestStock;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestStockDetailRequest {
    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long qty;

    @ApiModelProperty(position = 3)
    private Long qtyUomId;

    @ApiModelProperty(position = 3)
    private Long conversion;
}
