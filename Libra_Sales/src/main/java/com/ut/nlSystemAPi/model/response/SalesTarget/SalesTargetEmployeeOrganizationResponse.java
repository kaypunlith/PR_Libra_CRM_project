package com.ut.nlSystemAPi.model.response.SalesTarget;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SalesTargetEmployeeOrganizationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

}