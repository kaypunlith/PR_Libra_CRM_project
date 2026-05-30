package com.ut.nlSystemAPi.model.filter.Report.Organization;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class CustomerBalanceReportFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private Long organizationGroupId;

    @ApiModelProperty(position = 15)
    private Long organizationId;

    @ApiModelProperty(position = 16)
    private Long classId;

    @ApiModelProperty(position = 17)
    private Integer adjustmentEntry;

    @ApiModelProperty(position = 18)
    private Integer input;

}
