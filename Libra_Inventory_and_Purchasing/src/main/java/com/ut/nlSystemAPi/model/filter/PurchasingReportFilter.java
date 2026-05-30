package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchasingReportFilter extends Filter {

    @ApiModelProperty(position = 11, hidden = true)
    private Long userId;

}
