package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportAttendanceFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long groupId;

    @ApiModelProperty(position = 20)
    private Long departmentId;
    
    @ApiModelProperty(position = 30)
    private Long positionId;

    @ApiModelProperty(position = 40)
    private Long employeeId;

    @ApiModelProperty(position = 100)
    private String dateFrom;

    @ApiModelProperty(position = 110)
    private String dateTo;

    @ApiModelProperty(position = 120)
    private Long show; // 1: detail 2: summary

    @ApiModelProperty(position = 130)
    private Long timeClock; // 1:check-in 2:check-out

}
