package com.ut.nlSystemAPi.model.request.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationUpdateRequest extends OrganizationRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}