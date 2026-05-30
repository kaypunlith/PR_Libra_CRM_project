package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceDetailRequest {

    @ApiModelProperty(position = 2)
    private Long uomId;

    @ApiModelProperty(position = 3)
    private Double amount;

    @ApiModelProperty(position = 3)
    private Double amountBefore;

    @ApiModelProperty(position = 4)
    private Double percentage;

    @ApiModelProperty(position = 5)
    private Double addOn;

}
