package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayrollBalanceList implements Serializable {

    @ApiModelProperty(position = 1)
    private Float currentSalary;

    @ApiModelProperty(position = 2)
    private Float increaseSalary;

    @ApiModelProperty(position = 3)
    private Double receiveSalary;

    @ApiModelProperty(position = 4)
    private Float totalAmountDeposit;

    @ApiModelProperty(position = 5)
    private Float totalLoanAmount;

    @ApiModelProperty(position = 6)
    private Float amountInstallment;

    @ApiModelProperty(position = 7)
    private Float providentFund;

    @ApiModelProperty(position = 7)
    private Float donate;

    @ApiModelProperty(position = 8)
    private Float total;

    @ApiModelProperty(position = 9)
    private Double grandTotal;

    @ApiModelProperty(position = 10)
    private List<ReportPayrollItem> payrollItemList;

}
