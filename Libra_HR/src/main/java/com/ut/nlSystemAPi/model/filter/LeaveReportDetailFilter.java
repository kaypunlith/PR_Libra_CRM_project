package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeaveReportDetailFilter {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private String dateFrom;

    @ApiModelProperty(position = 3)
    private String dateTo;
}
