package com.ut.nlSystemAPi.model.request.serviceTerminate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ServiceTerminateRequest {

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private Long type;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String reason;

    @ApiModelProperty(position = 5)
    private List<ServiceTerminateDetailRequest> detailRequests;
}