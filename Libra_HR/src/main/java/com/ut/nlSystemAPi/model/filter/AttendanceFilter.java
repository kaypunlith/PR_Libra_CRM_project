package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttendanceFilter extends Filter {

    @ApiModelProperty(position = 10)
    private Long groupId;

    @ApiModelProperty(position = 20)
    private Long departmentId;

    @ApiModelProperty(position = 30)
    private Long positionId;

    @ApiModelProperty(position = 40)
    private Long employeeId;

    @ApiModelProperty(position = 50)
    private String dateFrom;

    @ApiModelProperty(position = 60)
    private String dateTo;
}
