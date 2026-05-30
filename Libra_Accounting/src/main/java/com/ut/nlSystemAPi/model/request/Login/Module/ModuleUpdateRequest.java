package com.ut.nlSystemAPi.model.request.Login.Module;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModuleUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long moduleTypeId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private Long ordering;

    @ApiModelProperty(position = 5)
    private Integer status;

}
