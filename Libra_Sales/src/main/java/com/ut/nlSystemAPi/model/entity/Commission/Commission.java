package com.ut.nlSystemAPi.model.entity.Commission;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Commission extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long locationGroupId;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 4)
    private String code;

    @ApiModelProperty(position = 5)
    private String startDate;

    @ApiModelProperty(position = 6)
    private String endDate;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 8)
    private Integer type;

    @ApiModelProperty(position = 8)
    private Integer recurring;

    @ApiModelProperty(position = 9)
    private Double target;

    @ApiModelProperty(position = 10)
    private Double amount;

    @ApiModelProperty(position = 11)
    private Double percent;

    @ApiModelProperty(position = 12)
    private Integer locationGroupApply;
}

