package com.ut.nlSystemAPi.model.request.Quotation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationTermConditionRequest {

    @ApiModelProperty(position = 1)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 2)
    private Long termConditionId;


}
