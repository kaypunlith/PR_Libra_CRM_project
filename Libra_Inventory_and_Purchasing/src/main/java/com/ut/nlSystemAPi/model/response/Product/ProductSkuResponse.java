package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductSkuResponse {

    @ApiModelProperty(position = 1)
    private Long uomId;

    @ApiModelProperty(position = 2)
    private String code;

}
