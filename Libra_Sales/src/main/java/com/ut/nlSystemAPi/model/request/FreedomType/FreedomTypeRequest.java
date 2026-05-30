package com.ut.nlSystemAPi.model.request.FreedomType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FreedomTypeRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;
}
