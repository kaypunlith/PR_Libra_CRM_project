package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeaveReportDetailResponse {

    @ApiModelProperty(position = 1)
    private String leaveTypeName;

    @ApiModelProperty(position = 2)
    private String DateFrom;

    @ApiModelProperty(position = 3)
    private String DateTo;

    @ApiModelProperty(position = 4)
    private Long numberOfDay;

    @ApiModelProperty(position = 5)
    private String description;

}
