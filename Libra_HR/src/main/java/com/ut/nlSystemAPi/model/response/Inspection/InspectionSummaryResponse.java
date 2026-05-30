package com.ut.nlSystemAPi.model.response.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionSummaryResponse {

    @ApiModelProperty(position = 1)
    private String groupName;

    @ApiModelProperty(position = 2)
    private String departmentName;

    @ApiModelProperty(position = 3)
    private String provinceName;

    @ApiModelProperty(position = 3)
    private Float servicePercentage;

    @ApiModelProperty(position = 4)
    private Float environtmentPercentage;

    @ApiModelProperty(position = 5)
    private Float productPercentage;

    @ApiModelProperty(position = 6)
    private Float totalScore;

    @ApiModelProperty(position = 7)
    private Float totalPercentage;

    @ApiModelProperty(position = 8)
    private String rank;

    @ApiModelProperty(position = 9)
    private Long qc;

}
