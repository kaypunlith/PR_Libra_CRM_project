package com.ut.nlSystemAPi.model.request.TermConditionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionApplyUpdateRequest{

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long moduleTypeId;

    @ApiModelProperty(position = 3)
    private Long termConditionTypeId;

    @ApiModelProperty(position = 4)
    private Long termConditionId;


}
