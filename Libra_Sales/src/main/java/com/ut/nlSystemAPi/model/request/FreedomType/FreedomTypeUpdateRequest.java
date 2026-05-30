package com.ut.nlSystemAPi.model.request.FreedomType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FreedomTypeUpdateRequest extends FreedomTypeRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
