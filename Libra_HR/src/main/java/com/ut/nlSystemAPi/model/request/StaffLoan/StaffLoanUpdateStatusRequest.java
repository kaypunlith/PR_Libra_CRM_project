package com.ut.nlSystemAPi.model.request.StaffLoan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffLoanUpdateStatusRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer status;
}
