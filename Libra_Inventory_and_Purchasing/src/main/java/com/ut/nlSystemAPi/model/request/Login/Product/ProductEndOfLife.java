package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductEndOfLife {

    @ApiModelProperty(position = 2)
    private Long isEndOfLife;

    @ApiModelProperty(position = 3)
    private Long id;
}
