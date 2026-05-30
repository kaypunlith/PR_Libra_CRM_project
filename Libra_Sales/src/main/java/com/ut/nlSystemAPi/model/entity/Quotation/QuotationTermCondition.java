package com.ut.nlSystemAPi.model.entity.Quotation;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuotationTermCondition extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 2)
    private Long termConditionId;

    @ApiModelProperty(position = 3)
    private Long quotationId;

}
