package com.ut.nlSystemAPi.model.request.Login.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceListDetails {

    @ApiModelProperty(position = 1, hidden = true)
    private Long productPriceListId;

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 3)
    private Double price;

    @ApiModelProperty(position = 3)
    private Long uomId;

}
