package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PgroupPriceInformation extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pgroupId;

    @ApiModelProperty(position = 3)
    private Long pgroupPriceSettingId;

    @ApiModelProperty(position = 4)
    private Long pgroupPriceTypeid;

    @ApiModelProperty(position = 5)
    private Double fromCost;

    @ApiModelProperty(position = 6)
    private Double toCost;

    @ApiModelProperty(position = 7)
    private Double percent;

    @ApiModelProperty(position = 8)
    private Double addOn;

    @ApiModelProperty(position = 9)
    private Long costMethod;

    @ApiModelProperty(position = 10)
    private Long setType;

    @ApiModelProperty(position = 11)
    private Long createBy;
    
}
