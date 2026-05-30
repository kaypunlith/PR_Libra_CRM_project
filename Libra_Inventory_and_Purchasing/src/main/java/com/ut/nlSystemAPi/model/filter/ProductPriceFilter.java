package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPriceFilter extends Filter {

    @ApiModelProperty(position = 5)
    private Long productId;

    @ApiModelProperty(position = 6, hidden = true)
    private Long priceTypeId;

}
