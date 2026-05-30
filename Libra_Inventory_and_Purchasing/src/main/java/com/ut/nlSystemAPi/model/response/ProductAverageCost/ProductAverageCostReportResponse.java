package com.ut.nlSystemAPi.model.response.ProductAverageCost;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductAverageCostReportResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 4)
    private String uom;

    @ApiModelProperty(position = 5)
    private Double lastCost;

    @ApiModelProperty(position = 6)
    private Double avgCost;

}
