package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepositHistory implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String historyAmount;

    @ApiModelProperty(position = 4)
    private Float totalAmountDeposit;

    @ApiModelProperty(position = 5)
    private Float totalAmountRequest;

}
