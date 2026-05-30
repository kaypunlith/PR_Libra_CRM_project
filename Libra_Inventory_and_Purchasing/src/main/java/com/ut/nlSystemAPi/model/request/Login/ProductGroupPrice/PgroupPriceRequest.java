package com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PgroupPriceRequest {

    @ApiModelProperty(position = 2)
    private Double fromCost;

    @ApiModelProperty(position = 3)
    private Double toCost;

    @ApiModelProperty(position = 4)
    private Double value;
}
