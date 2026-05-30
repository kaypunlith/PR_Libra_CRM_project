package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceListProductDetailFilter extends FilterBase {

    @ApiModelProperty(position = 10)
    private Long productPriceListId;

    @ApiModelProperty(position = 11)
    private Long companyId;

    @ApiModelProperty(position = 12)
    private Long productGroupId;

    @ApiModelProperty(position = 13)
    private Long priceTypeId;

}
