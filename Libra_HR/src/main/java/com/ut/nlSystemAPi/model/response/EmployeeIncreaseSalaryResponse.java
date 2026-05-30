package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeIncreaseSalaryResponse implements Serializable {

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private Float increaseSalary;
}
