package com.ut.nlSystemAPi.model.request.Login.AccountClosingDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountClosingDateSettingDetailRequest {

    @ApiModelProperty(position = 1)
    private Long accountType;

    @ApiModelProperty(position = 2)
    private Long chartAccountId;

    @ApiModelProperty(position = 3)
    private BigDecimal debit;

    @ApiModelProperty(position = 4)
    private BigDecimal credit;

    @ApiModelProperty(position = 5)
    private String memo;

    @ApiModelProperty(position = 6)
    private Long classId;

}
