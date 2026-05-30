package com.ut.nlSystemAPi.model.entity.Opportunities;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long opportunityId;

    @ApiModelProperty(position = 3)
    private Long pipelineId;

    @ApiModelProperty(position = 4)
    private Long stageId;

    @ApiModelProperty(position = 5)
    private Double probability;
}
