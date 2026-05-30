package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuDropdownResponse {
    @ApiModelProperty(position = 1)
    private Long skuId;

    @ApiModelProperty(position = 2)
    private String skuName;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private String productName;

    @ApiModelProperty(position = 4)
    private Double lastCost;


}
