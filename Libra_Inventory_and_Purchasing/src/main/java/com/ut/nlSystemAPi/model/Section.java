package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Section extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long warehouseId;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private String locationName;

    @ApiModelProperty(position = 4)
    private Long isForSale;

}
