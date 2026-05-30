package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LandedCost extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long poType;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private Long landedCostTypeId;

    @ApiModelProperty(position = 6)
    private String reference;

    @ApiModelProperty(position = 7)
    private Long supplyId;

    @ApiModelProperty(position = 9)
    private Long apId;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Double totalAmount;

}
