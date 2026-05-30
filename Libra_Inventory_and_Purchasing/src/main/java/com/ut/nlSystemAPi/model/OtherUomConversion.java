package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtherUomConversion extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long mainUom;

    @ApiModelProperty(position = 3)
    private Long otherUom;

    @ApiModelProperty(position = 3)
    private Long value;

    @ApiModelProperty(position = 5)
    private Long isSmallUom;

}
