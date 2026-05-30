package com.ut.nlSystemAPi.model.request.DeliverySchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryScheduleRequest {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 1)
    private String startDate;

    @ApiModelProperty(position = 2)
    private String startTime;

    @ApiModelProperty(position = 3)
    private String endDate;

    @ApiModelProperty(position = 4)
    private String endTime;

    @ApiModelProperty(position = 5)
    private String title;

    @ApiModelProperty(position = 6)
    private String privacy;

    @ApiModelProperty(position = 5)
    private String backgroundColor;

    @ApiModelProperty(position = 8)
    private List<DeliveryScheduleDetailRequest> details;

}