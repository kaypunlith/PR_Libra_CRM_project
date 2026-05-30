package com.ut.nlSystemAPi.model.request.EmployeeCalendar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeCalendarRequest {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Integer isRepeatable;

    @ApiModelProperty(position = 4)
    private Integer repeatType;

    @ApiModelProperty(position = 5)
    private List<String> repeatDays;

    @ApiModelProperty(position = 6)
    private List<Long> marketIds;

    @ApiModelProperty(position = 7)
    private String dateFrom;

    @ApiModelProperty(position = 8)
    private String dateTo;

    @ApiModelProperty(position = 9)
    private String description;
}
