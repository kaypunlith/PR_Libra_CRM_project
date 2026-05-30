package com.ut.nlSystemAPi.model.request.Inspection;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubInspectionRequest {

    private Long id;

    @ApiModelProperty(position = 1)
    private Long inspectionId;

    @ApiModelProperty(position = 2)
    private Long subInspectionId;

    @ApiModelProperty(position = 3)
    private Float totalScore;

    @ApiModelProperty(position = 4)
    private Float percentage;
}
