package com.ut.nlSystemAPi.model.request.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryNotePickOneRequest {

    @ApiModelProperty(position = 1)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 2)
    private Long salesInvoiceDetailId;

    @ApiModelProperty(position = 3)
    private Long organizationId;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 6)
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private List<DeliveryNotePickOneDetailRequest> details;

}

