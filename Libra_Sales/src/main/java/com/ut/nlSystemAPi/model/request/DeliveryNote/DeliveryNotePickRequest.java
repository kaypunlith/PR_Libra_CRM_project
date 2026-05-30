package com.ut.nlSystemAPi.model.request.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class DeliveryNotePickRequest {

    @ApiModelProperty(position = 1)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 4)
    private Long customerContactId;

    @ApiModelProperty(position = 5)
    private String shipTo;

    @ApiModelProperty(position = 6)
    private String note;

}

