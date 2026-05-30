package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductSkuRequest {
    @ApiModelProperty(position = 2)
    private String code;
}
