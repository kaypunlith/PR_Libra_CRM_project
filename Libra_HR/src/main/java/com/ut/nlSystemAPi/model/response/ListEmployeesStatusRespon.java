package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListEmployeesStatusRespon {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String filedType;

    @ApiModelProperty(position = 4)
    private String description;
}
