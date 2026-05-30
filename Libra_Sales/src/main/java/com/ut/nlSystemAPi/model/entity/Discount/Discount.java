package com.ut.nlSystemAPi.model.entity.Discount;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Discount extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String description;

    @ApiModelProperty(position = 5)
    private Double percent;

    @ApiModelProperty(position = 6)
    private Double amount;
}