package com.ut.nlSystemAPi.model.request.Opportunities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OpportunityTaskUpdateRequest extends OpportunityTaskRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
