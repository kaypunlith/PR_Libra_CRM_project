package com.ut.nlSystemAPi.model.entity.SaleInvoice;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleInvoiceTermCondition extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 2)
    private Long termConditionId;

    @ApiModelProperty(position = 3)
    private Long saleInvoiceId;

}
