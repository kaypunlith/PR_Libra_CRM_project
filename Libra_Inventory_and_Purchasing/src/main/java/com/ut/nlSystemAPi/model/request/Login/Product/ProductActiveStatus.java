package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductActiveStatus {

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long isActive;

}
