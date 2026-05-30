package com.ut.nlSystemAPi.model.request.Login.ProductBrand;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductBrandUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;
}
