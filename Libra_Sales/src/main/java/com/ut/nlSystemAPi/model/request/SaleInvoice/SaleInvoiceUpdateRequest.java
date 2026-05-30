package com.ut.nlSystemAPi.model.request.SaleInvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleInvoiceUpdateRequest extends SaleInvoiceRequest {

    @ApiModelProperty(position = 1)
    private Integer isUpdateView;

}
