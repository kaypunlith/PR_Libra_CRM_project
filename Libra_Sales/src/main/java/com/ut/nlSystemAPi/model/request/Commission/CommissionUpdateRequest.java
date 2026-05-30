package com.ut.nlSystemAPi.model.request.Commission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionUpdateRequest extends CommissionRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}

