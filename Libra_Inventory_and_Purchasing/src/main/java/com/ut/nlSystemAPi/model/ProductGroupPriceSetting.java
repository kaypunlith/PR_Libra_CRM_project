package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductGroupPriceSetting {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 1)
    private Long priceTypeId;

    @ApiModelProperty(position = 1)
    private Long uomId;

    @ApiModelProperty(position = 2)
    private Double amount;

    @ApiModelProperty(position = 3)
    private Double amountBefore;

    @ApiModelProperty(position = 4)
    private Double percent;

    @ApiModelProperty(position = 5)
    private Double addOn;

    @ApiModelProperty(position = 6)
    private Long setType;


}
