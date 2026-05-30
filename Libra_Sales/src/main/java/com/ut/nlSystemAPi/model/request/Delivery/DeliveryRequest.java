package com.ut.nlSystemAPi.model.request.Delivery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 4)
    private Long warehouseId;

    @ApiModelProperty(position = 5)
    private Long customerGroupId;

    @ApiModelProperty(position = 6)
    private Long customerId;

    @ApiModelProperty(position = 7)
    private Long deliveryId;

    @ApiModelProperty(position = 7)
    private List<Long> salesInvoiceIds;
}
