package com.ut.nlSystemAPi.model.request.MembershipCardLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCardLevelRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Double discountPercent;

    @ApiModelProperty(position = 4)
    private Double bdDiscountPercent;

    @ApiModelProperty(position = 5)
    private Double amount;

    @ApiModelProperty(position = 6)
    private Double point;

    @ApiModelProperty(position = 7)
    private Double requiredPoint;

    @ApiModelProperty(position = 8)
    private String memo;

    @ApiModelProperty(position = 9)
    private String color;
}
