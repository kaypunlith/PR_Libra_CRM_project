package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LandedCostServices extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long landedCostId;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 4)
    private Long serviceId;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 9)
    private Long conversion;

    @ApiModelProperty(position = 10)
    private Double smallValUom;

    @ApiModelProperty(position = 11)
    private Double landedCost;

    @ApiModelProperty(position = 12)
    private Double unitCost;

}
