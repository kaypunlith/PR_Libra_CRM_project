package com.ut.nlSystemAPi.model.response.serviceTerminateResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceTerminateDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long serviceId;

    @ApiModelProperty(position = 3)
    private String serviceName;

    @ApiModelProperty(position = 3)
    private Long sectionId;

    @ApiModelProperty(position = 3)
    private String sectionName;

    @ApiModelProperty(position = 3)
    private Long sku;

    @ApiModelProperty(position = 3)
    private double unitPrice;

    @ApiModelProperty(position = 3)
    private String description;

    @ApiModelProperty(position = 3)
    private Long status;




}