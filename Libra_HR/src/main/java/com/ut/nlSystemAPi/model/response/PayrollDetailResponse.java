package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollDetailResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long payrollId;

    @ApiModelProperty(position = 3)
    private Long employeeId;

    @ApiModelProperty(position = 4)
    private Long payrollItemId;

    @ApiModelProperty(position = 5)
    private String payrollItemName;

    @ApiModelProperty(position = 6)
    private Float amount;

}
