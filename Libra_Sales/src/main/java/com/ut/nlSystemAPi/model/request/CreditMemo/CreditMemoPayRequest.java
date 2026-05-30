package com.ut.nlSystemAPi.model.request.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMemoPayRequest {
    @ApiModelProperty(position = 1)
    private Long creditMemoId;

    @ApiModelProperty(position = 3)
    private Long exchangeRateId;

    @ApiModelProperty(position = 4)
    private Long currencyCenterId;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private Double paidUsd;

    @ApiModelProperty(position = 5)
    private Double paidOther;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private Double balance;

    @ApiModelProperty(position = 5)
    private Double balanceOther;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 7)
    private String aging;

    @ApiModelProperty(position = 8)
    private String moduleCode;

}
