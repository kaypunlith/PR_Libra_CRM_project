package com.ut.nlSystemAPi.model;

import java.io.Serializable;
import java.util.List;

import com.ut.nlSystemAPi.model.base.BaseId;
import com.ut.nlSystemAPi.model.request.WorkShiftRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class WorkShift extends WorkShiftRequest implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;


    @ApiModelProperty(position = 1)
    private Long workShiftType;

    @ApiModelProperty(position = 10)
    private String name;

    @ApiModelProperty(position = 20)
    private String timeFrom;

    @ApiModelProperty(position = 30)
    private String timeTo;

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

    @ApiModelProperty(position = 110)
    private List<BaseId> dayIdLists;

}
