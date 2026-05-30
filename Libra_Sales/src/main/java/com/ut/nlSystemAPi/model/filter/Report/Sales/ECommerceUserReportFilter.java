package com.ut.nlSystemAPi.model.filter.Report.Sales;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ECommerceUserReportFilter extends Filter {

    @ApiModelProperty(position = 10)
    private String dobFrom;

    @ApiModelProperty(position = 11)
    private String dobTo;

}
