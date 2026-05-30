package com.ut.nlSystemAPi.model.filter.Report.Sales;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpenInvoiceByRepReportFilter extends Filter {

    @ApiModelProperty(position = 11)
    private String date;

    @ApiModelProperty(position = 13)
    private Integer status;

    @ApiModelProperty(position = 14)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private Long organizationId;

    @ApiModelProperty(position = 15)
    private Long organizationGroupId;

    @ApiModelProperty(position = 16)
    private Long warehouseId;

    @ApiModelProperty(position = 18)
    private Long createdBy;

}