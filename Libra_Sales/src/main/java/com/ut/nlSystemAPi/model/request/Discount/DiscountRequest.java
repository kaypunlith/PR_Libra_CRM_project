package com.ut.nlSystemAPi.model.request.Discount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiscountRequest {

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