package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductChartAccount {

    @ApiModelProperty
    private Long chartAccountId;

    @ApiModelProperty
    private Long productId;

    @ApiModelProperty
    private Long accountType;
}
