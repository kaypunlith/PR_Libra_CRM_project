package com.ut.nlSystemAPi.model.filter;
import com.ut.nlSystemAPi.model.base.Filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventorySampleProductFilter extends Filter {

    @ApiModelProperty(position = 10)
    private String upc;

    @ApiModelProperty(position = 12)
    private Long locationGroupId;

    @ApiModelProperty(position = 11)
    private String sku;

    @ApiModelProperty(position = 13, hidden = true)
    private Long userId;
}
