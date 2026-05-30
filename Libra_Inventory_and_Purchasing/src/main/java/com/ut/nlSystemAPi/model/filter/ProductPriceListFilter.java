package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceListFilter extends Filter {


    @ApiModelProperty(position = 11)
    private Long companyId;

    @ApiModelProperty(position = 12)
    private Long productGroupId;

    @ApiModelProperty(position = 13)
    private Long priceTypeId;

    @ApiModelProperty(position = 14)
    private Long organizationId;

    @ApiModelProperty(position = 15)
    private Long status;

    @ApiModelProperty(position = 15)
    private Long viewByUser = 0L;


}
