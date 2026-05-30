package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductICSRequest {

    @ApiModelProperty(position = 2)
    private Long accountType;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;
}
