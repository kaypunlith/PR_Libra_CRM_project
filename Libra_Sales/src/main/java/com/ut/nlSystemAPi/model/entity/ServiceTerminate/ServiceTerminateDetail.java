package com.ut.nlSystemAPi.model.entity.ServiceTerminate;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceTerminateDetail extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long serviceId;

    @ApiModelProperty(position = 3)
    private Long sku;

}