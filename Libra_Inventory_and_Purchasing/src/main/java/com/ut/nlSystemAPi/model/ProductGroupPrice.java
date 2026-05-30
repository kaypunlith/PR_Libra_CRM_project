package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductGroupPrice extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pgroupId;

    @ApiModelProperty(position = 3)
    private Long priceTypeId;

    @ApiModelProperty(position = 4)
    private Long setType;

    @ApiModelProperty(position = 5)
    private Long costMethod;

    @ApiModelProperty(position = 6)
    private Long ApplyToAllProduct;

}
