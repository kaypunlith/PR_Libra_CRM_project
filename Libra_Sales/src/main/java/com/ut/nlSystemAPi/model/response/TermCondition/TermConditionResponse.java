package com.ut.nlSystemAPi.model.response.TermCondition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long typeId;

    @ApiModelProperty(position = 4)
    private String typeName;

    @ApiModelProperty(position = 5)
    private String description;

}
