package com.ut.nlSystemAPi.model.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttendanceResponse implements Serializable {

    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 20)
    private String profile;

    @ApiModelProperty(position = 30)
    private String employeeId;

    @ApiModelProperty(position = 40)
    private String employeeName;

    @ApiModelProperty(position = 50)
    private Long gender;

    @ApiModelProperty(position = 60)
    private String position;

    @ApiModelProperty(position = 70)
    private String department;

    @ApiModelProperty(position = 75)
    private Long departmentId;

    @ApiModelProperty(position = 76)
    private String departmentName;

    @ApiModelProperty(position = 80)
    private Long timeClock;

    @ApiModelProperty(position = 90)
    private String shift;

    @ApiModelProperty(position = 95)
    private Long shiftId;

    @ApiModelProperty(position = 96)
    private String shiftName;

    @ApiModelProperty(position = 90)
    private String shiftTimeFrom;

    @ApiModelProperty(position = 90)
    private String shiftTimeTo;

    @ApiModelProperty(position = 100)
    private String date;

    @ApiModelProperty(position = 110)
    private String time;

    @ApiModelProperty(position = 120)
    private Long status;

    @ApiModelProperty(position = 130)
    private Long minute;

    @ApiModelProperty(position = 140)
    private Long totalWorkingMinute;

    @ApiModelProperty(position = 150)
    private String workDuration;

}
