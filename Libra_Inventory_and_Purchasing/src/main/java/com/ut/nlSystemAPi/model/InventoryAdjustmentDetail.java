package com.ut.nlSystemAPi.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class InventoryAdjustmentDetail {


    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long cycleProductId;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 4)
    private String expiredDate;

    @ApiModelProperty(position = 5)
    private Long newQty;

    @ApiModelProperty(position = 5)
    private String lotNumber;

    @ApiModelProperty(position = 6)
    private Long qtyDifference;

    @ApiModelProperty(position = 7)
    private Long currentQty;

}
