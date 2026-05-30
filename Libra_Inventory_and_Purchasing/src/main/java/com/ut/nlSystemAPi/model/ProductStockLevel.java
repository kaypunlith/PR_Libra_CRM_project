package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductStockLevel extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 3)
    private Long stockLevelId;

    @ApiModelProperty(position = 4)
    private Long qty;
}
