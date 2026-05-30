package com.ut.nlSystemAPi.model.request.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApplyWithInvoiceRequest {

    @ApiModelProperty(position = 1)
    private Long creditMemoId;

    @ApiModelProperty(position = 2)
    private String applyDate;

    @ApiModelProperty(position = 3)
    private List<ApplyWithInvoiceDetailRequest> details;

}
