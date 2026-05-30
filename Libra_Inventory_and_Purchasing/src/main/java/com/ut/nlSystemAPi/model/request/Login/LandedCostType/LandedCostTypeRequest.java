package com.ut.nlSystemAPi.model.request.Login.LandedCostType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class LandedCostTypeRequest {

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String name;

}
