package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ValuationReportFilter extends Filter {

    @ApiModelProperty(position = 7)
    private String date;

    @ApiModelProperty(position = 8)
    private Long productId;

    @ApiModelProperty(position = 8)
    private Long view;
}
