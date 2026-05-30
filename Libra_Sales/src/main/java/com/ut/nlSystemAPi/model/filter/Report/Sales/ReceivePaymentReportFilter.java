package com.ut.nlSystemAPi.model.filter.Report.Sales;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentReportFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Integer adjustEntry;

    @ApiModelProperty(position = 14)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private Long organizationId;

    @ApiModelProperty(position = 15)
    private Long organizationGroupId;

    @ApiModelProperty(position = 16)
    private Long classId;

}