package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import com.ut.nlSystemAPi.model.base.FilterBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UomsProductFilter extends FilterBase {

    @ApiModelProperty(position = 10)
    private Long productId;

    @ApiModelProperty(position = 10)
    private Long priceTypeId;

}
