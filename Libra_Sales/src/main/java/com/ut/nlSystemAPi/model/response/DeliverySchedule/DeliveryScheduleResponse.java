package com.ut.nlSystemAPi.model.response.DeliverySchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryScheduleResponse {

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

    @ApiModelProperty(position = 7)
    private String privacy;

    @ApiModelProperty(position = 7)
    private String backgroundColor;

    @ApiModelProperty(position = 8)
    private Long isClose;

    @ApiModelProperty(position = 8)
    private Long totalSO;

    @ApiModelProperty(position = 9)
    private String pvRequestNumber;

    @ApiModelProperty(position = 9)
    private Double totalAmount;

    @ApiModelProperty(position = 10)
    private String comment;

    @ApiModelProperty(position = 10)
    private String bookedBy;

    @ApiModelProperty(position = 11)
    private List<DeliveryScheduleDetailResponse> details;
}
