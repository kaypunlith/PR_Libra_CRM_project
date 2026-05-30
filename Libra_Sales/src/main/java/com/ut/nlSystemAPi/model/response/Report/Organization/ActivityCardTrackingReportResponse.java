package com.ut.nlSystemAPi.model.response.Report.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ActivityCardTrackingReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private String organizationName;

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String contactName;

    @ApiModelProperty(position = 3)
    private String status;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 3)
    private String position;

    @ApiModelProperty(position = 3)
    private String subject;

    @ApiModelProperty(position = 3)
    private String result;

    @ApiModelProperty(position = 3)
    private String other;

    @ApiModelProperty(position = 3)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private List<ActivityCardTrackingReportResponse> details;

}