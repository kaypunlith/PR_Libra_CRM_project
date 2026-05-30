package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductICSResponse {

    @ApiModelProperty(position = 2)
    private Long accountType;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private String chartAccountName;

}
