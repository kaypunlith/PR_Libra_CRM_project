package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseBillDropdownFilter extends VendorContactFilter {

    @ApiModelProperty(position = 10)
    private Long isEr;

    @ApiModelProperty(position = 10)
    private Long isLandedCost;

}
