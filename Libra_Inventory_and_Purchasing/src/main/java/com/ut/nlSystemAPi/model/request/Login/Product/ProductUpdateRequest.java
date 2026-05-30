package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductUpdateRequest extends ProductRequest{

    @ApiModelProperty(position = 1)
    private Long id;

}
