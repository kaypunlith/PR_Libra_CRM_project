package com.ut.nlSystemAPi.model.response.APSchedule;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class APScheduleResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long branchId;

    @ApiModelProperty(position = 1)
    private String branchName;

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

    @ApiModelProperty(position = 10)
    private String createdDate;

    @ApiModelProperty(position = 11)
    private String createdBy;

    @ApiModelProperty(position = 12)
    private String modifiedDate;

    @ApiModelProperty(position = 13)
    private String modifiedBy;

    @ApiModelProperty(position = 14)
    private List<APScheduleResponseDetails> details;


}
