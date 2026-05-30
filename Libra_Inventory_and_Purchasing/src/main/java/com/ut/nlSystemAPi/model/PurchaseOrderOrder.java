package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseOrderOrder {

    @ApiModelProperty
    private Long purchaseOrderId;

    @ApiModelProperty
    private Long orderId;
}
