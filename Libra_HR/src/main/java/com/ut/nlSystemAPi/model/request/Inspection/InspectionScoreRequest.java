package com.ut.nlSystemAPi.model.request.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionScoreRequest {

    @ApiModelProperty(position = 1)
    private Long subSubInspectionId;

    @ApiModelProperty(position = 2)
    private Long score;
}
