package com.ut.nlSystemAPi.model.response.Product;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceDetailResponse {

    @ApiModelProperty(position = 1)
    private Long uomId;

    @ApiModelProperty(position = 2)
    private String uom;

    @ApiModelProperty(position = 3)
    private Double amount;

    @ApiModelProperty(position = 4)
    private Double amountBefore;

    @ApiModelProperty(position = 5)
    private Double percentage;

    @ApiModelProperty(position = 6)
    private Double addOn;

    @ApiModelProperty(position = 1)
    private Long setType;

}
