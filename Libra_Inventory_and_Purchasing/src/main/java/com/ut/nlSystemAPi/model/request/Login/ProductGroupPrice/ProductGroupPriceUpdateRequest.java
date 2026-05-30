package com.ut.nlSystemAPi.model.request.Login.ProductGroupPrice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductGroupPriceUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pgroupId;

    @ApiModelProperty(position = 3)
    private Long priceTypeId;

    @ApiModelProperty(position = 4)
    private Long setType;

    @ApiModelProperty(position = 5)
    private Long costMethod;

    @ApiModelProperty(position = 6)
    private Long applyToAllProduct;

    @ApiModelProperty(position = 7)
    List<PgroupPriceRequest> pgroupPriceRequests;
}
