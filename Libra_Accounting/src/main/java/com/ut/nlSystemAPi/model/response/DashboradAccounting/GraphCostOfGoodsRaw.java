package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GraphCostOfGoodsRaw {

    @ApiModelProperty(position = 1)
    private Integer month;

    @ApiModelProperty(position = 2)
    private Long parentGroupId;

    @ApiModelProperty(position = 3)
    private String parentGroupName;

    @ApiModelProperty(position = 4)
    private Double amount;
}

