package com.ut.nlSystemAPi.model.response.serviceTerminateResponse;

import com.ut.nlSystemAPi.model.entity.ServiceTerminate.ServiceTerminateDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ServiceTerminateResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private String customerName;

    @ApiModelProperty(position = 4)
    private Long serviceId;

    @ApiModelProperty(position = 5)
    private String serviceName;

    @ApiModelProperty(position = 6)
    private String date;

    @ApiModelProperty(position = 7)
    private Long type;

    @ApiModelProperty(position = 7)
    private String typeName;

    @ApiModelProperty(position = 7)
    private String reason;


    @ApiModelProperty(position = 6)
    private Long status;

    @ApiModelProperty(position = 13)
    private String created;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 15)
    private String modified;

    @ApiModelProperty(position = 16)
    private String modifiedBy;


    @ApiModelProperty(position = 6)
    private String printStatus;


    @ApiModelProperty(position = 17)
    private List<ServiceTerminateDetailResponse> details;


}