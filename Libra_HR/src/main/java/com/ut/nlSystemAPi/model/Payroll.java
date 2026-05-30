package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Payroll extends BaseModel implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long employeesId;

    @ApiModelProperty(position = 2)
    private Long departmentId;

    @ApiModelProperty(position = 3)
    private String empId;

    @ApiModelProperty(position = 4)
    private String nameKh;

    @ApiModelProperty(position = 5)
    private String nameEn;

    @ApiModelProperty(position = 6)
    private String gender;

    @ApiModelProperty(position = 7)
    private Long positionId;

    @ApiModelProperty(position = 8)
    private String positionName;

    @ApiModelProperty(position = 9)
    private String dateOfWork;

    @ApiModelProperty(position = 10)
    private Float currentSalary;

    @ApiModelProperty(position = 11)
    private Float increaseSalary;

    @ApiModelProperty(position = 12)
    private Long paidType;

    @ApiModelProperty(position = 13)
    private String accountNumber;

    @ApiModelProperty(position = 14)
    private String accountId;

    @ApiModelProperty(position = 16)
    private Float totalAmount;

    @ApiModelProperty(position = 17)
    private String payDate;

    @ApiModelProperty(position = 18)
    private String note;
}
