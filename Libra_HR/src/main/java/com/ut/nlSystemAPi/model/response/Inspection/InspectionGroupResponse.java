package com.ut.nlSystemAPi.model.response.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InspectionGroupResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String shortcuts;

    @ApiModelProperty(position = 4)
    private Long percentage;

    @ApiModelProperty(position = 5)
    private Long servicePercentage;

    @ApiModelProperty(position = 6)
    private Long environmentPercentage;

    @ApiModelProperty(position = 7)
    private Long productPercentage;

    @ApiModelProperty(position = 8)
    private List<SubInspectionResponse> subInspectionResponses;
}
