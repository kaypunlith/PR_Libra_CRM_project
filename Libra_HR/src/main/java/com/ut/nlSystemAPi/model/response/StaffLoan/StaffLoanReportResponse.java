package com.ut.nlSystemAPi.model.response.StaffLoan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StaffLoanReportResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long termMonths;

    @ApiModelProperty(position = 5)
    private String employeeNameEn;

    @ApiModelProperty(position = 5)
    private String employeeCode;

    @ApiModelProperty(position = 5)
    private String departmentName;

    @ApiModelProperty(position = 5)
    private String positionName;

    @ApiModelProperty(position = 5)
    private Integer gender;

    @ApiModelProperty(position = 4)
    private Integer paybackPeriod;

    @ApiModelProperty(position = 5)
    private String paybackDate;

    @ApiModelProperty(position = 6)
    private Double loanAmount;

    @ApiModelProperty(position = 6)
    private Double interestRate;

    @ApiModelProperty(position = 6)
    private Double paidAmount;

    @ApiModelProperty(position = 6)
    private Double balance;

    @ApiModelProperty(position = 8)
    private String loanDate;

    @ApiModelProperty(position = 9)
    private String monthlyPayment;

    @ApiModelProperty(position = 12)
    private Integer status;
}
