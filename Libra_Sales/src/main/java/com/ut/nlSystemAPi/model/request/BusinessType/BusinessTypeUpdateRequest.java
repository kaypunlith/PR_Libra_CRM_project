package com.ut.nlSystemAPi.model.request.BusinessType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessTypeUpdateRequest extends BusinessTypeRequest{

    @ApiModelProperty(position = 1)
    private Long id;

}