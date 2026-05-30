package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeaveReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 6)
    private String employeeNameKh;

    @ApiModelProperty(position = 7)
    private String employeeNameEn;

    @ApiModelProperty(position = 4)
    private Long employeeId;

    @ApiModelProperty(position = 4)
    private Float numberOfDay;

    @ApiModelProperty(position = 5)
    private String employeeCode;

    @ApiModelProperty(position = 3)
    private Long AL;

    @ApiModelProperty(position = 4)
    private Long SL;

    @ApiModelProperty(position = 5)
    private Long ML;

    @ApiModelProperty(position = 6)
    private Long PL;

    @ApiModelProperty(position = 7)
    private Long MGL;

    @ApiModelProperty(position = 8)
    private Long FL;

    @ApiModelProperty(position = 11)
    private String DateFrom;

    @ApiModelProperty(position = 10)
    private String DateTo;

}
