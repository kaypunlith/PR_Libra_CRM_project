package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeAdjustBeginning {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private Float amount;

    @ApiModelProperty(position = 5)
    private Long totalOfMonths;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private Long createdBy;

    @ApiModelProperty(position = 9)
    private String modified;

    @ApiModelProperty(position = 10)
    private Long modifiedBy;

    @ApiModelProperty(position = 11)
    private int isActive;

}
