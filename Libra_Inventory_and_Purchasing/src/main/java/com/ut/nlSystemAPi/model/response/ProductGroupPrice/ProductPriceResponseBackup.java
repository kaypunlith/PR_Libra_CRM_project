package com.ut.nlSystemAPi.model.response.ProductGroupPrice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductPriceResponseBackup {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;

    @ApiModelProperty(position = 14)
    private Long setType;

    @ApiModelProperty(position = 1)
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
