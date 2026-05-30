package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepositRequestReport implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String employeesCode;

    @ApiModelProperty(position = 3)
    private String nameKh;

    @ApiModelProperty(position = 4)
    private String nameEn;

    @ApiModelProperty(position = 5)
    private String dateOfWork;

    @ApiModelProperty(position = 6)
    private String plateNumber;

    @ApiModelProperty(position = 7)
    private Float newSalary;

    @ApiModelProperty(position = 8)
    private Float depositSalary;

    @ApiModelProperty(position = 9)
    private Float oldSalary;

    @ApiModelProperty(position = 10)
    private String date;

    @ApiModelProperty(position = 11, hidden = true)
    private Float adjustBeginningAmount;

    @ApiModelProperty(position = 27, hidden = true)
    private Float cashAmount;

    @ApiModelProperty(position = 28, hidden = true)
    private Long numOfMonth;
}
