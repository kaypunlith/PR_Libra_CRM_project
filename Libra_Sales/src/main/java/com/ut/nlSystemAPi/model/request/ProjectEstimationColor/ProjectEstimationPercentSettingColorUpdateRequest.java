package com.ut.nlSystemAPi.model.request.ProjectEstimationColor;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectEstimationPercentSettingColorUpdateRequest extends ProjectEstimationPercentSettingColorRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}