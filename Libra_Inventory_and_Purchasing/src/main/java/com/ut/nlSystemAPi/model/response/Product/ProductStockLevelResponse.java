package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductStockLevelResponse {

    @ApiModelProperty(position = 1)
    private Long stockLevelId;

    @ApiModelProperty(position = 2)
    private String stockLevelName;

    @ApiModelProperty(position = 3)
    private Double qty;
}
