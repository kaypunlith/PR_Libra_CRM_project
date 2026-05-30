package com.ut.nlSystemAPi.model.request.BusinessType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusinessTypeRequest {

    @ApiModelProperty(position = 2)
    private String name;
}