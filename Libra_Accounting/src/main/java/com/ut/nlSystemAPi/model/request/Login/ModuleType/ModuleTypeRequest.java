package com.ut.nlSystemAPi.model.request.Login.ModuleType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModuleTypeRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Long ordering;

    @ApiModelProperty(position = 3)
    private Integer status;

}
