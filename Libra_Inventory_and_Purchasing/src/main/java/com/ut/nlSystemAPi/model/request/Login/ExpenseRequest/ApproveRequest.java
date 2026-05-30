package com.ut.nlSystemAPi.model.request.Login.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApproveRequest  {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long typeApproval;

    @ApiModelProperty(position = 3)
    private Double totalAmountApprove;

    @ApiModelProperty(position = 4)
    private List<DepositApproveRequest> deposit;

    @ApiModelProperty(position = 5, hidden = true)
    private Long approveBy;

}
