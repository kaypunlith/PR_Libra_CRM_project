package com.ut.nlSystemAPi.model.entity.ServiceTerminate;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ServiceTerminate extends BaseModel {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private Long type;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String reason;

}