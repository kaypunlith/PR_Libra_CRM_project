package com.ut.nlSystemAPi.model.request.TermConditionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionTypeUpdateRequest extends TermConditionTypeRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}
