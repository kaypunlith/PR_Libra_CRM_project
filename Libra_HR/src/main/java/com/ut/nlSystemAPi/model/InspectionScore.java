package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InspectionScore extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long inspectionId;

    @ApiModelProperty(position = 3)
    private Long subSubInspectionId;

    @ApiModelProperty(position = 4)
    private Long score;

}
