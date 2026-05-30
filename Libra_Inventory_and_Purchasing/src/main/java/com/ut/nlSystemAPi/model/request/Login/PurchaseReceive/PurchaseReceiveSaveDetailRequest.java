package com.ut.nlSystemAPi.model.request.Login.PurchaseReceive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseReceiveSaveDetailRequest {
    @ApiModelProperty(position = 1)
    private Long purchaseOrderDetailId;

    @ApiModelProperty(position = 3)
    private Long qtyReceive;

    @ApiModelProperty(position = 3)
    private Double unitCost;

    @ApiModelProperty(position = 3)
    private Double newCost;

    @ApiModelProperty(position = 3)
    private String dateReceive;

    @ApiModelProperty(position = 3)
    private String expiredDate;
}
