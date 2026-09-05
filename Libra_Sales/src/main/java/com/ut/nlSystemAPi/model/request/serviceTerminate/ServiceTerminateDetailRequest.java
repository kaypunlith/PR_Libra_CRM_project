package com.ut.nlSystemAPi.model.request.serviceTerminate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ServiceTerminateDetailRequest {

    @ApiModelProperty(position = 2)
    private Long serviceId;

    @ApiModelProperty(position = 2)
    private Integer status;

    @ApiModelProperty(position = 2)
    private Long sku;





}