package com.ut.nlSystemAPi.model.response.Service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceShiftResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String name;

}