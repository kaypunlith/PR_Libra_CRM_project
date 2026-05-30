package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleOrderDropdownFilter extends Filter {

    @ApiModelProperty(position = 6)
    private Integer deliverySchedule;

}
