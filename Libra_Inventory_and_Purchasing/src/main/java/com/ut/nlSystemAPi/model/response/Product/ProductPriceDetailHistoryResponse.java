package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceDetailHistoryResponse {

    @ApiModelProperty(position = 3)
    private Integer setType;

    @ApiModelProperty(position = 3)
    private Double amount;

    @ApiModelProperty(position = 4)
    private Double percentage;

    @ApiModelProperty(position = 5)
    private Double addOn;

    @ApiModelProperty(position = 3)
    private Double unitCost;

    @ApiModelProperty(position = 3)
    private Double estimateCost;

}
