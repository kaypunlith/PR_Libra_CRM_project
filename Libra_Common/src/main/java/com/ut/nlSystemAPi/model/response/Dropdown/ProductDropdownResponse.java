package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductDropdownResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String sku;

    @ApiModelProperty(position = 4)
    private String upc;
}
