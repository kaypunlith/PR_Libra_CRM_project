package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositUpdateStatus {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long status;

    @ApiModelProperty(position = 3, hidden = true)
    private String modified;

    @ApiModelProperty(position = 4, hidden = true)
    private Long modifiedBy;

    @ApiModelProperty(position = 5, hidden = true)
    private int isActive;

}
