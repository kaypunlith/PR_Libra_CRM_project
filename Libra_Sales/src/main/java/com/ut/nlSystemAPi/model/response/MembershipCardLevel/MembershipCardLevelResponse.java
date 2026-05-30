package com.ut.nlSystemAPi.model.response.MembershipCardLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCardLevelResponse {

    @ApiModelProperty(position = 1)
    private Long id;

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

    @ApiModelProperty(position = 10)
    private String created;

    @ApiModelProperty(position = 11)
    private String createdBy;

    @ApiModelProperty(position = 12)
    private String modified;

    @ApiModelProperty(position = 13)
    private String modifiedBy;
}
