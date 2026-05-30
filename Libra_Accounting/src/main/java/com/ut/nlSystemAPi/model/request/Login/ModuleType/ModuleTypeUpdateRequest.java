package com.ut.nlSystemAPi.model.request.Login.ModuleType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModuleTypeUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long ordering;

    @ApiModelProperty(position = 4)
    private Integer status;

}
