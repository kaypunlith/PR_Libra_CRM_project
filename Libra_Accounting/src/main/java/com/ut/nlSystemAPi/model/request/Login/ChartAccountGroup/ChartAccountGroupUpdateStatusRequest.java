package com.ut.nlSystemAPi.model.request.Login.ChartAccountGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountGroupUpdateStatusRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer status;
}
