package com.ut.nlSystemAPi.model.request.MembershipCardLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCardLevelUpdateRequest extends MembershipCardLevelRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
