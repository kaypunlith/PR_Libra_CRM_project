package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DepositRequestStatus {

    @ApiModelProperty(position = 1)
    private Long depositRequestId;

    @ApiModelProperty(position = 2, hidden = true)
    private Long modifiedBy;

    @ApiModelProperty(position = 3)
    private List<DepositRequestStatusByEmp> depositRequestUpdateStatus;

}
