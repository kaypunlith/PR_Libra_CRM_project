package com.ut.nlSystemAPi.model.request.Delivery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryDetailRequest {

    @ApiModelProperty(position = 1)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 2)
    private Long salesInvoiceDetailId;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String expiredDate;

    @ApiModelProperty(position = 7)
    private Long qty;
}
