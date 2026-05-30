package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductGroupPriceFilter extends Filter {

    @ApiModelProperty(position = 11)
    private Long productGroupId;

    @ApiModelProperty(position = 12)
    private Long priceTypeId;

}
