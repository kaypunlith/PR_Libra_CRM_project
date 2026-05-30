package com.ut.nlSystemAPi.model.request.Login.ProductBrand;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductBrandRequest {

    @ApiModelProperty(position = 1)
    private String name;
}
