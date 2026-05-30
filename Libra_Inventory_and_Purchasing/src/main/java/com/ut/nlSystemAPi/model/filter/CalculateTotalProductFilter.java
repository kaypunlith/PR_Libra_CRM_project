package com.ut.nlSystemAPi.model.filter;


import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CalculateTotalProductFilter extends Filter {

    @ApiModelProperty(position = 12)
    private Double fromCost;

    @ApiModelProperty(position = 13)
    private Double toCost;

    @ApiModelProperty(position = 14)
    private Long groupId;

    @ApiModelProperty(position = 15)
    private Long setType;

    @ApiModelProperty(position = 16)
    private Double value;

}
