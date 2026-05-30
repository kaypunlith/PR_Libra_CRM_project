package com.ut.nlSystemAPi.model.request.Lead;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadUpdateRequest extends LeadRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
