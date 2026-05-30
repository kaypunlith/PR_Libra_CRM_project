package com.ut.nlSystemAPi.model.request.Login.TransferSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TransferScheduleUpdateRequest {
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

    @ApiModelProperty(position = 9)
    private List<TransferScheduleDetailRequest> transferScheduleDetails;
}
