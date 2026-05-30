package com.ut.nlSystemAPi.model.request.LeadGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeadGroupUpdateRequest extends LeadGroupRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
