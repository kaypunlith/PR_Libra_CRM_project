package com.ut.nlSystemAPi.model.response.UomConversion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UomConversionList {

    @ApiModelProperty(position = 1)
    private Long otherUom;

    @ApiModelProperty(position = 2)
    private Long value;
}
