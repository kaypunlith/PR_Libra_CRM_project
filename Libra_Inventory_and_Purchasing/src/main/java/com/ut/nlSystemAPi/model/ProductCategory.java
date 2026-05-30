package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductCategory {

    @ApiModelProperty
    private Long categoryId;

    @ApiModelProperty
    private Long productId;

}
