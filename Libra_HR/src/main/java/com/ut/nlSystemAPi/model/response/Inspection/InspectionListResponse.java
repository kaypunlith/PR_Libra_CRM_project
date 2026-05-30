package com.ut.nlSystemAPi.model.response.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionListResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String createdBy;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private String employeeName;

    @ApiModelProperty(position = 2)
    private String employeeCode;

    @ApiModelProperty(position = 2)
    private String profile;

    @ApiModelProperty(position = 2)
    private Long positionId;

    @ApiModelProperty(position = 2)
    private String positionName;

    @ApiModelProperty(position = 3)
    private String groupName;

    @ApiModelProperty(position = 4)
    private String departmentName;

    @ApiModelProperty(position = 5)
    private String createdDate;

    @ApiModelProperty(position = 6)
    private String provinceName;

    @ApiModelProperty(position = 7)
    private Float servicePercentage;

    @ApiModelProperty(position = 8)
    private Float environmentPercentage;

    @ApiModelProperty(position = 9)
    private Float productPercentage;

    @ApiModelProperty(position = 10)
    private String assessmentDate;

    @ApiModelProperty(position = 11)
    private String assessmentNote;
}
