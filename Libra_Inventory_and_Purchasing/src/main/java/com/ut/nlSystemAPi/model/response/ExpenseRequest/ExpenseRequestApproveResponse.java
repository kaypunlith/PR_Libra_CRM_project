package com.ut.nlSystemAPi.model.response.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpenseRequestApproveResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pvRequestId;

    @ApiModelProperty(position = 3)
    private Double depositPercentage;

    @ApiModelProperty(position = 4)
    private Double depositAmount;

    @ApiModelProperty(position = 5)
    private Long payBillApply;

    @ApiModelProperty(position = 6)
    private Long isApproved;

    @ApiModelProperty(position = 7)
    private String approved;

    @ApiModelProperty(position = 8)
    private String approvedBy;

}
