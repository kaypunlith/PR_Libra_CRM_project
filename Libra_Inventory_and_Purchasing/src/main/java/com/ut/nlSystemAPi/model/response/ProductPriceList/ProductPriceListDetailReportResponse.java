package com.ut.nlSystemAPi.model.response.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceListDetailReportResponse {

    @ApiModelProperty(position = 1)
    private Long priceTypeId;

    @ApiModelProperty(position = 2)
    private String priceTypeName;

    @ApiModelProperty(position = 3)
    private Long productPriceId;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 5)
    private Long setType;

    @ApiModelProperty(position = 5)
    private Double amount;

}
