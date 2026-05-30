package com.ut.nlSystemAPi.model.request.MembershipCard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MembershipCardUpdateRequest extends MembershipCardRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
