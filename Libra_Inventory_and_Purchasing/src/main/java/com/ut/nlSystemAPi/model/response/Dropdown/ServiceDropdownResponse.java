package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String sku;

    @ApiModelProperty(position = 3)
    private String upc;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private String uom;

    @ApiModelProperty(position = 6)
    private Double unitPrice;

    @ApiModelProperty(position = 7)
    private Long sectionId;

    @ApiModelProperty(position = 8)
    private Long type;
}
