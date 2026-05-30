package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayBillReportFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateTo;

    @ApiModelProperty(position = 10)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private Long companyId;

    @ApiModelProperty(position = 13)
    private Long vendorId;

    @ApiModelProperty(position = 14)
    private Long classId;

    @ApiModelProperty(position = 15)
    private Long isAdj;

}
