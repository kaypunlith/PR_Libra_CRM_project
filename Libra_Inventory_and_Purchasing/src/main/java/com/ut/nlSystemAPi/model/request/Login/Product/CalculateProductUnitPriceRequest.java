package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CalculateProductUnitPriceRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 3)
    private Long priceTypeId;
}
