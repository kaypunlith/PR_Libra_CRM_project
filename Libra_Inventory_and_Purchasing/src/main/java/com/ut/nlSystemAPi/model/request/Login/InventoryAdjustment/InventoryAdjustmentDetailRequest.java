package com.ut.nlSystemAPi.model.request.Login.InventoryAdjustment;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class InventoryAdjustmentDetailRequest {

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 5)
    private String expiredDate;

    @ApiModelProperty(position = 6)
    private Long newQty;

    @ApiModelProperty(position = 7)
    private Long qtyDifference;

    @ApiModelProperty(position = 8)
    private Long currentQty;

}
