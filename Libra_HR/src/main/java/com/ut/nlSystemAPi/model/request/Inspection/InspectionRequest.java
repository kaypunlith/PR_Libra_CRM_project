package com.ut.nlSystemAPi.model.request.Inspection;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionRequest {

    @ApiModelProperty(position = 1)
    private Long groupId;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private Long departmentId;

    @ApiModelProperty(position = 4)
    private Long provinceId;

    @ApiModelProperty(position = 5)
    private String assessmentDate;

    @ApiModelProperty(position = 6)
    private String assessmentNote;

    @ApiModelProperty(position = 7)
    List<InspectionScoreRequest> inspectionScoreRequests;

}
