package com.ut.nlSystemAPi.model.request.TermConditionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TermConditionApplyRequest {

    @ApiModelProperty(position = 1)
    private Long moduleTypeId;

    @ApiModelProperty(position = 2)
    private List<TermConditionApplyDetailRequest> details;

}
