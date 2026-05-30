package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ExpenseRequestFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long status;

    @ApiModelProperty(position = 13)
    private Long viewByUser = 0L;

    @ApiModelProperty(position = 13)
    private Long isReferSaleOrder = 0L;

}
