package com.ut.nlSystemAPi.model.request.TermCondition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionUpdateRequest extends TermConditionRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
