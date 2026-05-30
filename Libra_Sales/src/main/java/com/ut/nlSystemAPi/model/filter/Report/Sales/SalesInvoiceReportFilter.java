package com.ut.nlSystemAPi.model.filter.Report.Sales;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesInvoiceReportFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Integer view;

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 12)
    private String dateTo;

    @ApiModelProperty(position = 13)
    private Long type;

    @ApiModelProperty(position = 13)
    private Integer status;

    @ApiModelProperty(position = 14)
    private Long organizationId;

    @ApiModelProperty(position = 15)
    private Long organizationGroupId;

    @ApiModelProperty(position = 16)
    private Long companyId;

    @ApiModelProperty(position = 17)
    private Long warehouseId;

    @ApiModelProperty(position = 18)
    private Long createdBy;

    @ApiModelProperty(position = 19)
    private Integer filterBy;

    @ApiModelProperty(position = 20)
    private Long departmentId;

}