package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ViewAttendanceResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 1)
    private String timeIn;

    @ApiModelProperty(position = 1)
    private String timeOut;

    @ApiModelProperty(position = 1)
    private Long status;

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 1)
    private Long workShiftId;

    @ApiModelProperty(position = 1)
    private Float shiftHour;

    @ApiModelProperty(position = 1)
    private Long minute;

    @ApiModelProperty(position = 1)
    private Float salaryDeduction;

    @ApiModelProperty(position = 1)
    private Float shiftDay;

    @ApiModelProperty(position = 1)
    private String timeScan;

    @ApiModelProperty(position = 1)
    private Long totalWorkingMinute;

}
