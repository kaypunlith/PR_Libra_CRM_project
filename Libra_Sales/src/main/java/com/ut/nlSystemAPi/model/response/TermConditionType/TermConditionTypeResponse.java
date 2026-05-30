package com.ut.nlSystemAPi.model.response.TermConditionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TermConditionTypeResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Integer allowDelete;
}
