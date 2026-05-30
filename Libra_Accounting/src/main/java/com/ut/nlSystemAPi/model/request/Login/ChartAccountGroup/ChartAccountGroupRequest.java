package com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountGroupRequest {

    @ApiModelProperty(position = 1)
    private Long accountTypeId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 1)
    private Integer expense;
}
