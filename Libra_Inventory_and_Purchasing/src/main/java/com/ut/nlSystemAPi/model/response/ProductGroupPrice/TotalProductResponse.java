package com.ut.nlSystemAPi.model.response.ProductGroupPrice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TotalProductResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String uom;

    @ApiModelProperty(position = 5)
    private Double unitCost;

    @ApiModelProperty(position = 6)
    private Double price;

}
