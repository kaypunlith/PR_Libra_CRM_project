package com.ut.nlSystemAPi.model.response.Inspection;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionFindResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long groupId;

    @ApiModelProperty(position = 3)
    private String groupName;

    @ApiModelProperty(position = 4)
    private Long employeeId;

    @ApiModelProperty(position = 5)
    private String employeeName;

    @ApiModelProperty(position = 6)
    private Long positionId;

    @ApiModelProperty(position = 7)
    private String positionName;

    @ApiModelProperty(position = 8)
    private Long departmentId;

    @ApiModelProperty(position = 9)
    private String departmentName;

    @ApiModelProperty(position = 10)
    private Long provinceId;

    @ApiModelProperty(position = 11)
    private String provinceName;

    @ApiModelProperty(position = 12)
    private Float servicePercentage;

    @ApiModelProperty(position = 9)
    private Float environtmentPercentage;

    @ApiModelProperty(position = 10)
    private Float productPercentage;

    @ApiModelProperty(position = 11)
    private Float serviceFullScore;

    @ApiModelProperty(position = 12)
    private Float environtmentFullScore;

    @ApiModelProperty(position = 13)
    private Float productFullScore;

    @ApiModelProperty(position = 14)
    private Float serviceTotalScore;

    @ApiModelProperty(position = 15)
    private Float environtmentTotalScore;

    @ApiModelProperty(position = 16)
    private Float productTotalScore;

    @ApiModelProperty(position = 17)
    private String rank;

    @ApiModelProperty(position = 18)
    private String assessmentDate;

    @ApiModelProperty(position = 23)
    private String assessmentNote;

    @ApiModelProperty(position = 24)
    private List<InspectionGroupResponse> inspectionGroupResponses;
}
