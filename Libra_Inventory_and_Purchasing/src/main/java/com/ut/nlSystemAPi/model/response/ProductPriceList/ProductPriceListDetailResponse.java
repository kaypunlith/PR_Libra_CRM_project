package com.ut.nlSystemAPi.model.response.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceListDetailResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String brand;

    @ApiModelProperty(position = 3)
    private String model;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 4)
    private String uom;

    @ApiModelProperty(position = 5)
    private Float price;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 6)
    private String description;

}
