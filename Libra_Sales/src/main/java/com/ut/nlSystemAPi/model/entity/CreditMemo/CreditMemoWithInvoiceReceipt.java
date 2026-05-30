package com.ut.nlSystemAPi.model.entity.CreditMemo;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMemoWithInvoiceReceipt extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long creditMemoId;

    @ApiModelProperty(position = 3)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 4)
    private Double totalCost;

    @ApiModelProperty(position = 5)
    private String applyDate;

}
