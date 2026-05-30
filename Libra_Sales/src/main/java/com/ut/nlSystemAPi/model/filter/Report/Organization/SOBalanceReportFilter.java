package com.ut.nlSystemAPi.model.filter.Report.Organization;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SOBalanceReportFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long type;

    @ApiModelProperty(position = 13)
    private Long organizationId;

}
