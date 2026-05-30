package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 1)
    private Long chartAccountTypeId;

    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 4)
    private Double balance;


}
