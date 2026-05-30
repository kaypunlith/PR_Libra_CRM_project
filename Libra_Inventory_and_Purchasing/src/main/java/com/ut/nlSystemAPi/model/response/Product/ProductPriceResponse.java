package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class ProductPriceResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private String priceTypeName;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private Double estimateCost;

    @ApiModelProperty(position = 14)
    private Long setType;

    @ApiModelProperty(position = 13)
    private Double lastSellingPrice;

    @ApiModelProperty(position = 15)
    private Long isCatalog;

    @ApiModelProperty(position = 18)
    private List<ProductPriceDetailResponse> details;

}
