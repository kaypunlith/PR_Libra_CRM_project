package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UomsDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long smallUomId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String abbr;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 6)
    private Double unitPrice;

    @ApiModelProperty(position = 6)
    private Double unitCost;

    @ApiModelProperty(position = 6)
    private Double estimateCost;

}
