package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long currencyId;

    @ApiModelProperty(position = 4)
    private String currencyName;

    @ApiModelProperty(position = 5)
    private String currencySymbol;

}
