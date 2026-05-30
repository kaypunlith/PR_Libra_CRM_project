package com.ut.nlSystemAPi.model.request.Commission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionEmployeeRequest {

    @ApiModelProperty(position = 1)
    private Long employeeId;
}

