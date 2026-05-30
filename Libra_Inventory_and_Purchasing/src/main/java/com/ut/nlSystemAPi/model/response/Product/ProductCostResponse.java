package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductCostResponse {

    @ApiModelProperty
    private Double unitCost;

    @ApiModelProperty
    private Double estimateCost;

    @ApiModelProperty
    private Long baseUomId;

    @ApiModelProperty
    private Long smallValUom;

}
