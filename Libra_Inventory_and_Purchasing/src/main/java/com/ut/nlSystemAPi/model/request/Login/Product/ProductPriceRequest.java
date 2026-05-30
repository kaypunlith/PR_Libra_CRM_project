package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductPriceRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;

    @ApiModelProperty(position = 3)
    private Long setType;

    @ApiModelProperty(position = 4)
    private List<ProductPriceDetailRequest> details;

}
