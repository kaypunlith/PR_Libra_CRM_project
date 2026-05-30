package com.ut.nlSystemAPi.model.response.UomConversion;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UomConversionDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long otherUomId;

    @ApiModelProperty(position = 4)
    private String otherUomName;

    @ApiModelProperty(position = 6)
    private Long value;

}
