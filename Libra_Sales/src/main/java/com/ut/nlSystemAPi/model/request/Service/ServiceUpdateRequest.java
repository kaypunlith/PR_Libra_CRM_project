package com.ut.nlSystemAPi.model.request.Service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceUpdateRequest extends ServiceRequest {

    @ApiModelProperty(position = 1)
    private Long id;

}