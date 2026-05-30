package com.ut.nlSystemAPi.model.request.ProjectEstimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectEstimationUpdateRequest extends ProjectEstimationRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}