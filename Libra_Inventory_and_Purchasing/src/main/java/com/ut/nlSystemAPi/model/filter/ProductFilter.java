package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductFilter extends Filter {

    @ApiModelProperty(position = 5)
    private Long show;

    @ApiModelProperty(position = 6)
    private Long isActive;

    @ApiModelProperty(position = 7)
    private Long groupId;

    @ApiModelProperty(position = 8)
    private String sku;

    @ApiModelProperty(position = 9)
    private String upc;

    @ApiModelProperty(position = 17)
    private Long viewByUser;
}
