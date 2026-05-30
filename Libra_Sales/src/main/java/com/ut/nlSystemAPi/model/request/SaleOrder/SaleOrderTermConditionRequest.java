package com.ut.nlSystemAPi.model.request.SaleOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleOrderTermConditionRequest {

    @ApiModelProperty(position = 1)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 2)
    private Long termConditionId;

}
