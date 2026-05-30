package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class ProductPriceHistoryResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private String priceTypeName;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String uom;

    @ApiModelProperty(position = 3)
    private Double amount;

    @ApiModelProperty(position = 4)
    private Double percentage;

    @ApiModelProperty(position = 5)
    private Double addOn;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private Double estimateCost;

    @ApiModelProperty(position = 13)
    private Double lastSellingPrice;

    @ApiModelProperty(position = 14)
    private Integer setType;

    @ApiModelProperty(position = 15)
    private String created;

    @ApiModelProperty(position = 16)
    private String createdBy;

}
