package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositDetailUpdate {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeesId;

    @ApiModelProperty(position = 3)
    private float amountRequest;

    @ApiModelProperty(position = 4)
    private Long providentFundId;

    @ApiModelProperty(position = 5)
    private Long status;

    @ApiModelProperty(position = 103, hidden = true)
    private String modified;

    @ApiModelProperty(position = 104, hidden = true)
    private Long modifiedBy;

    @ApiModelProperty(position = 106, hidden = true)
    private int isActive;

}
