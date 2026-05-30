package com.ut.nlSystemAPi.model.request.Login.ChartAccountType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChartAccountTypeUpdateRequest {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Integer status;
}
