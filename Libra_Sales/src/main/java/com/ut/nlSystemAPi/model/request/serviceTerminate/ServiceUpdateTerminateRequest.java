package com.ut.nlSystemAPi.model.request.serviceTerminate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceUpdateTerminateRequest extends ServiceTerminateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}