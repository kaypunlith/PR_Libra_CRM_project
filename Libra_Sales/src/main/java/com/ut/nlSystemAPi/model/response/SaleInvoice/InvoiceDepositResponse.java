package com.ut.nlSystemAPi.model.response.SaleInvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InvoiceDepositResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String code;

}