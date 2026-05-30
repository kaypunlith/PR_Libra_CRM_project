package com.ut.nlSystemAPi.model.request.Login.ARAPSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ARAPScheduleRequest {
    @ApiModelProperty(position = 1)
    private Long branchId;
    @ApiModelProperty(position = 2)
    private String startDate;
    @ApiModelProperty(position = 3)
    private String endDate;
    @ApiModelProperty(position = 4)
    private String startTime;
    @ApiModelProperty(position = 5)
    private String endTime;
    @ApiModelProperty(position = 7)
    private String title;
    @ApiModelProperty(position = 8)
    private String backgroundColor;
    @ApiModelProperty(position = 9)
    private String privacy;
    @ApiModelProperty(position = 14)
    private List<ARAPScheduleRequestDetails> invoices;
}
