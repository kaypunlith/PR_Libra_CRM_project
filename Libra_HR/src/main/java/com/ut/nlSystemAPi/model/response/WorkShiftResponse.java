package com.ut.nlSystemAPi.model.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkShiftResponse  {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 10)
    private String name;

    @ApiModelProperty(position = 20)
    private String timeFrom;

    @ApiModelProperty(position = 30)
    private String timeTo;

    @ApiModelProperty(position = 40)
    private Long workShiftTypeId;

    @ApiModelProperty(position = 50)
    private String workShiftTypeName;

    @ApiModelProperty(position = 40)
    private Long lateCheckingTime;

    @ApiModelProperty(position = 50)
    private Long leaveEarlyTime;

    @ApiModelProperty(position = 60)
    private String beginningCheckIn;

    @ApiModelProperty(position = 70)
    private String endingCheckIn;

    @ApiModelProperty(position = 80)
    private String beginningCheckOut;

    @ApiModelProperty(position = 90)
    private String endingCheckOut;

    @ApiModelProperty(position = 100)
    private String description;

    @ApiModelProperty(position = 200)
    private List<DayResponse> days;

    @ApiModelProperty(position = 210)
    private String duration;

    @ApiModelProperty(position = 220)
    private String created;

    @ApiModelProperty(position = 230)
    private String createdBy;

    @ApiModelProperty(position = 240)
    private String modified;

    @ApiModelProperty(position = 250)
    private String modifiedBy;

}
