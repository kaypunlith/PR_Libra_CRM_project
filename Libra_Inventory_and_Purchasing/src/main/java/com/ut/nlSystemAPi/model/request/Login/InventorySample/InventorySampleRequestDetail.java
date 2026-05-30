package com.ut.nlSystemAPi.model.request.Login.InventorySample;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventorySampleRequestDetail {

    @ApiModelProperty(position = 1,hidden = true)
    private Long id;

    @ApiModelProperty(position = 2,hidden = true)
    private Long inventorySampleProductId;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 3)
    private Long locationId;

    @ApiModelProperty(position = 2)
    private String expiredDate;

    @ApiModelProperty(position = 12)
    private Long newQty;

    @ApiModelProperty(position = 13)
    private Long adjustQty;

    @ApiModelProperty(position = 14)
    private Long qtyOnHand;
}
