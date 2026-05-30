package com.ut.nlSystemAPi.model.request.ProjectEstimationColor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectEstimationPercentSettingColorRequest {

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String color;

    @ApiModelProperty(position = 3)
    private Double percentFrom;

    @ApiModelProperty(position = 4)
    private Double percentTo;

}