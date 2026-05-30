package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductCodeNameResponse {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Double unitCost;

}
