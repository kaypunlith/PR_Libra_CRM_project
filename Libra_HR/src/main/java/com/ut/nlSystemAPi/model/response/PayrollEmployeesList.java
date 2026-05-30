package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayrollEmployeesList implements Serializable {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String empId;

    @ApiModelProperty(position = 3)
    private String nameKh;

    @ApiModelProperty(position = 4)
    private String nameEn;

    @ApiModelProperty(position = 5)
    private String gender;

    @ApiModelProperty(position = 6)
    private String positionName;

    @ApiModelProperty(position = 7)
    private String dateOfWork;

    @ApiModelProperty(position = 8)
    private Float currentSalary;

    @ApiModelProperty(position = 9)
    private Float increaseSalary;

    @ApiModelProperty(position = 10)
    private Float totalAmount;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 11)
    private Long paidType;

    @ApiModelProperty(position = 12)
    private List<ReportPayrollItemType> payrollTypes;

}
