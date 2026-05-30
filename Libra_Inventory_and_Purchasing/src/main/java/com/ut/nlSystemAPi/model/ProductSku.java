package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductSku {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long uomId;

}
