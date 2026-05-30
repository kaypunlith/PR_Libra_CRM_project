package com.ut.nlSystemAPi.model.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeaveRequestResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 11)
    private String dateFrom;

    @ApiModelProperty(position = 10)
    private String dateTo;

    @ApiModelProperty(position = 15)
    private Integer status;

    @ApiModelProperty(position = 4)
    private Long employeeId;

    @ApiModelProperty(position = 4)
    private Float numberOfDay;

    @ApiModelProperty(position = 5)
    private String employeeCode;

    @ApiModelProperty(position = 6)
    private String employeeNameKh;

    @ApiModelProperty(position = 7)
    private String employeeNameEn;

    @ApiModelProperty(position = 8)
    private String description;

    @ApiModelProperty(position = 9)
    private Long departmentId;

    @ApiModelProperty(position = 10)
    private String departmentName;

    @ApiModelProperty(position = 9)
    private Long positionId;

    @ApiModelProperty(position = 9)
    private String positionName;

    @ApiModelProperty(position = 1)
    private Long leaveTypeId;

    @ApiModelProperty(position = 12)
    private String leaveTypeName;

    @ApiModelProperty(position = 13)
    private String createdBy;

    @ApiModelProperty(position = 14)
    private String created;

    @ApiModelProperty(position = 13)
    private String modifiedBy;

    @ApiModelProperty(position = 14)
    private String modified;

    @ApiModelProperty(position = 50)
    private List<LeaveRequestDetailResponse> leaveRequestDetailResponses;
}
