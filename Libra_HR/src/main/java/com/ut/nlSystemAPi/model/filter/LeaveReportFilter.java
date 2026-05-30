package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeaveReportFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long groupId;

    @ApiModelProperty(position = 11)
    private Long departmentId;

    @ApiModelProperty(position = 13)
    private String dateFrom;

    @ApiModelProperty(position = 14)
    private String dateTo;
}
