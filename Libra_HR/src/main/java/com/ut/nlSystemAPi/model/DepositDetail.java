package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeesId;

    @ApiModelProperty(position = 3)
    private float amountRequest;

    @ApiModelProperty(position = 3)
    private String remark;

    @ApiModelProperty(position = 4)
    private Long depositRequestId;

}
