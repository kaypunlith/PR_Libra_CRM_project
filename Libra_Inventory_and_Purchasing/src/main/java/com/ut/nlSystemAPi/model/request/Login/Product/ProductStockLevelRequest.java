package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductStockLevelRequest {

    @ApiModelProperty(position = 1)
    private Long stockLevelId;

    @ApiModelProperty(position = 2)
    private Long qty;
}
