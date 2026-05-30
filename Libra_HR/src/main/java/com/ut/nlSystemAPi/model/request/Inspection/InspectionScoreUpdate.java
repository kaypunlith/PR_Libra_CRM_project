package com.ut.nlSystemAPi.model.request.Inspection;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InspectionScoreUpdate extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long groupId;

    @ApiModelProperty(position = 3)
    private Long employeeId;

    @ApiModelProperty(position = 4)
    private Long departmentId;

    @ApiModelProperty(position = 5)
    private Long provinceId;

    @ApiModelProperty(position = 6)
    private String assessmentDate;

    @ApiModelProperty(position = 7)
    private String assessmentNote;

    @ApiModelProperty(position = 8)
    private Float servicePercentage;

    @ApiModelProperty(position = 5)
    private Float enviromentPercentage;

    @ApiModelProperty(position = 6)
    private Float productPercentage;

    @ApiModelProperty(position = 4)
    private Float serviceScore;

    @ApiModelProperty(position = 5)
    private Float enviromentScore;

    @ApiModelProperty(position = 6)
    private Float productScore;

}
