package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseFilter {

    @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 10)
    private Long status;

}
