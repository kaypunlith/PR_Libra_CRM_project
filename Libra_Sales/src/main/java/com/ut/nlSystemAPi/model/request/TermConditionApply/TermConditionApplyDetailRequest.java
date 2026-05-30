package com.ut.nlSystemAPi.model.request.TermConditionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionApplyDetailRequest {

    @ApiModelProperty(position = 3)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 4)
    private Long termConditionId;

}
