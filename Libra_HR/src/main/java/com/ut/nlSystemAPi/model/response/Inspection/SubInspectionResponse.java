package com.ut.nlSystemAPi.model.response.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubInspectionResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Float score;

    @ApiModelProperty(position = 4)
    private Float percentage;

    @ApiModelProperty(position = 5)
    private Long inspectionGroupId;

    @ApiModelProperty(position = 8)
    private Float totalPercentage;

    @ApiModelProperty(position = 9)
    private Float fullScore;

    @ApiModelProperty(position = 101)
    private List<Long> staticScore;

    @ApiModelProperty(position = 102)
    private List<SubSubInspectionResponse> subSubInspectionResponses;

}
