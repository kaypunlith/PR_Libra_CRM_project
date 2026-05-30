package com.ut.nlSystemAPi.model.request.StaffLoan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffLoanUpdateRequest extends StaffLoanRequest {

    @ApiModelProperty(position = 0)
    private Long id;
}
