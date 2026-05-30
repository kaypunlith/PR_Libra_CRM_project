package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeConnectedDeviceResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private String userName;

    @ApiModelProperty(position = 2)
    private String clientId;

    @ApiModelProperty(position = 3)
    private String deviceName;

}
