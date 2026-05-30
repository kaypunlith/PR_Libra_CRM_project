package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityStageUpdateRequest extends OpportunityStageRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
