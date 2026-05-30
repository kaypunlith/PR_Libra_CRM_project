package com.ut.nlSystemAPi.model.request.StaffLoan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffLoanRequest {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private Long positionId;

    @ApiModelProperty(position = 3)
    private Long paybackPeriod;

    @ApiModelProperty(position = 4)
    private Double amount;
}
