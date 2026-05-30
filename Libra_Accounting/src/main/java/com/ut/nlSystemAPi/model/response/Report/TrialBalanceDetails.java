package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrialBalanceDetails {
    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 1)
    private Double debit;

    @ApiModelProperty(position = 1)
    private Double credit;

    @ApiModelProperty(position = 1)
    private Double balance;


}
