package com.ut.nlSystemAPi.model.request.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryNoteStockRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long warehouseId;
}

