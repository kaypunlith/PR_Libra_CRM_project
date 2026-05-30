package com.ut.nlSystemAPi.model.response.EmployeeCalendar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeCalendarResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private String employeeCode;

    @ApiModelProperty(position = 4)
    private String employeeName;

    @ApiModelProperty(position = 5)
    private String departmentName;

    @ApiModelProperty(position = 6)
    private String dateFrom;

    @ApiModelProperty(position = 7)
    private String dateTo;

    @ApiModelProperty(position = 8)
    private Integer isRepeatable;

    @ApiModelProperty(position = 9)
    private Integer repeatType;

    @ApiModelProperty(position = 10)
    private String description;

    @ApiModelProperty(position = 11)
    private String repeatDays;

    @ApiModelProperty(position = 12)
    private String marketNames;

    @ApiModelProperty(position = 13)
    private List<String> repeatDayList;

    @ApiModelProperty(position = 14)
    private List<Long> marketIds;

    @ApiModelProperty(position = 15)
    private String created;

    @ApiModelProperty(position = 16)
    private String modified;

    @ApiModelProperty(position = 17)
    private String createdBy;

    @ApiModelProperty(position = 18)
    private String modifiedBy;

    @ApiModelProperty(position = 19)
    private Integer isActive;
}
