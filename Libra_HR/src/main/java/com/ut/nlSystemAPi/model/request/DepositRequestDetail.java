package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositRequestDetail {

    @ApiModelProperty(position = 1)
    private Long employeesId;

    @ApiModelProperty(position = 2)
    private float amountRequest;

    @ApiModelProperty(position = 3)
    private Long status;

    @ApiModelProperty(position = 3)
    private String remark;

}
