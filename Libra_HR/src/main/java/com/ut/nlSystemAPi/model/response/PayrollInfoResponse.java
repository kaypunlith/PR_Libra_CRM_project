package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayrollInfoResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private Long payrollId;

    @ApiModelProperty(position = 3)
    private Float oldSalary;

    @ApiModelProperty(position = 4)
    private Float increaseSalary;

    @ApiModelProperty(position = 5)
    private Float totalAmount;

    @ApiModelProperty(position = 6)
    private String note;
}
