package com.ut.nlSystemAPi.model.response.ARSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ARScheduleResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private String branchName;

    @ApiModelProperty(position = 4)
    private String startDate;

    @ApiModelProperty(position = 5)
    private String endDate;

    @ApiModelProperty(position = 6)
    private String startTime;

    @ApiModelProperty(position = 7)
    private String endTime;

    @ApiModelProperty(position = 8)
    private String title;

    @ApiModelProperty(position = 9)
    private String backgroundColor;

    @ApiModelProperty(position = 10)
    private String privacy;

    @ApiModelProperty(position = 11)
    private String createdDate;

    @ApiModelProperty(position = 12)
    private String createdBy;

    @ApiModelProperty(position = 13)
    private String modifiedDate;

    @ApiModelProperty(position = 14)
    private String modifiedBy;

    @ApiModelProperty(position = 15)
    private List<ARScheduleResponseDetails> details;


}
