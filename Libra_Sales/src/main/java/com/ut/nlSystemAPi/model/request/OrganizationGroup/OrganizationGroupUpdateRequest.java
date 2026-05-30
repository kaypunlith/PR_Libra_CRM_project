package com.ut.nlSystemAPi.model.request.OrganizationGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationGroupUpdateRequest extends OrganizationGroupRequest {
    @ApiModelProperty(position = 1)
    private Long id;
}