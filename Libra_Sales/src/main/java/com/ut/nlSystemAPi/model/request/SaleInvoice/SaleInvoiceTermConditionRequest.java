package com.ut.nlSystemAPi.model.request.SaleInvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleInvoiceTermConditionRequest {

    @ApiModelProperty(position = 1)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 2)
    private Long termConditionId;

}
