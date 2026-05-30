package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeUpdateRequest extends EmployeeRequest {

    @ApiModelProperty(position = 1)
    private Long id;
}
