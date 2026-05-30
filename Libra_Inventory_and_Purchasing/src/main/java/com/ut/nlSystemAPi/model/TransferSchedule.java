package com.ut.nlSystemAPi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ut.nlSystemAPi.model.base.BaseModel;
import com.ut.nlSystemAPi.model.response.TransferScheduleResponse.TransferScheduleDetailResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.Serializers;

import java.time.LocalDate;
import java.util.List;

@Data
public class TransferSchedule extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String startDate;

    @ApiModelProperty(position = 3)
    private String startTime;

    @ApiModelProperty(position = 4)
    private String endDate;

    @ApiModelProperty(position = 5)
    private String endTime;

    @ApiModelProperty(position = 6)
    private String title;

    @ApiModelProperty(position = 6)
    private String backgroundColor;

    @ApiModelProperty(position = 7)
    private String privacy;

    @ApiModelProperty(position = 8)
    private Long pvRequestId;

    @ApiModelProperty(position = 9)
    private Long isClose;

    @ApiModelProperty(position = 10)
    private String booked;


}
