package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.List;

@Data
public class AuditTrailReportFilter extends Filter {
    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private List<Long> branchId;

    @ApiModelProperty(position = 8)
    private Long createBy;

    @ApiModelProperty(position = 8)
    private List<Long> typeSelect;


}
