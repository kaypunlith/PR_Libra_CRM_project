package com.ut.nlSystemAPi.model.request.SalesTarget;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesTargetUpdateRequest extends SalesTargetRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}