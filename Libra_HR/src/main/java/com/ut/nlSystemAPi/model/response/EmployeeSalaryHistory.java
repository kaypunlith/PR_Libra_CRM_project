package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeSalaryHistory implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Float salary;

    @ApiModelProperty(position = 3)
    private Float increaseSalary;

    @ApiModelProperty(position = 4)
    private Float currentSalary;

    @ApiModelProperty(position = 5)
    private String date;

}
