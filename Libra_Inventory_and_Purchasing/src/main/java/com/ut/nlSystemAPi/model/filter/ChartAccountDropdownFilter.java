package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChartAccountDropdownFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long isPurchasing;

    @ApiModelProperty(position = 10)
    private Long isDeposit;

    @ApiModelProperty(position = 11)
    private List<Long> typeIds;

}
