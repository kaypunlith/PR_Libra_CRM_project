package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class ProductGroupPriceSettingResponse {

    @ApiModelProperty(position = 1)
    private Long priceTypeId;

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
