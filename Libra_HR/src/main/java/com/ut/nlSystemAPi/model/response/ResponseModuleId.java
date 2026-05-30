package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseModuleId {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 4)
    private String sysCode;

}
