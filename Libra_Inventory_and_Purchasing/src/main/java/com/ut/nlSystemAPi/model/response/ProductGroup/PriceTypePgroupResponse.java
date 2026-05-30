package com.ut.nlSystemAPi.model.response.ProductGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceTypePgroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Double percent;

    @ApiModelProperty(position = 4)
    private Double addOn;

    @ApiModelProperty(position = 5)
    private Long setType;

    @ApiModelProperty(position = 6)
    private Double fromCost;

    @ApiModelProperty(position = 7)
    private Double toCost;

}
