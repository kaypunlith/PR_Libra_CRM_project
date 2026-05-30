package com.ut.nlSystemAPi.model.request.Login.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepositApproveRequest {
    @ApiModelProperty(position = 4)
    private Long id;

    @ApiModelProperty(position = 4, hidden = true)
    private Long pvRequestId;

    @ApiModelProperty(position = 4)
    private Double depositPercentage;

    @ApiModelProperty(position = 5)
    private Double depositAmount;

    @ApiModelProperty(position = 5)
    private Long payBillApply;

    @ApiModelProperty(position = 6)
    private Long isApprove;

    @ApiModelProperty(position = 7, hidden = true)
    private Long approveBy;

}
