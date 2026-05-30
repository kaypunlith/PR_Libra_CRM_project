package com.ut.nlSystemAPi.model.request.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApplyWithInvoiceDetailRequest {

    @ApiModelProperty(position = 3)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 5)
    private Double paid;

}
