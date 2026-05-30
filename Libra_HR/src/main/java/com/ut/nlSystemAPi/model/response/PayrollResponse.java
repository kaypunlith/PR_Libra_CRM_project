package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayrollResponse implements Serializable {

    @ApiModelProperty(position = 2)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private Long payrollId;

    @ApiModelProperty(position = 3)
    private String code;

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

    @ApiModelProperty(position = 10)
    private Float secondWorkShiftSalary;

    @ApiModelProperty(position = 11)
    private Float increaseSalary;

    @ApiModelProperty(position = 20)
    private Float allowance;

    @ApiModelProperty(position = 12)
    private Long paidType;

    @ApiModelProperty(position = 13)
    private String accountNumber;

    @ApiModelProperty(position = 14)
    private String accountId;

    @ApiModelProperty(position = 15)
    private Long deposit;

    @ApiModelProperty(position = 16)
    private Float cashAmount;

    @ApiModelProperty(position = 17)
    private Long numOfMonth;

    @ApiModelProperty(position = 18)
    private Long donate;

    @ApiModelProperty(position = 18)
    private Long proFund;

    @ApiModelProperty(position = 19)
    private Float totalAmount;

    @ApiModelProperty(position = 20)
    private String remark;

    @ApiModelProperty(position = 20)
    private String note;

    @ApiModelProperty(position = 15)
    private int isSaved;

    @ApiModelProperty(position = 22, hidden = true)
    private Float moneyDeposit;

    @ApiModelProperty(position = 22, hidden = true)
    private Float moneyDonate;

    @ApiModelProperty(position = 22, hidden = true)
    private Float moneyProFund;

    @ApiModelProperty(position = 22, hidden = true)
    private Long employeeStatusId;

    @ApiModelProperty(position = 23)
    private String bankImage;

    @ApiModelProperty(position = 24)
    private String bankImageName;

    @ApiModelProperty(position = 24)
    private Long isTelegram;

    @ApiModelProperty(position = 21)
    private List<PayrollItemType> payrollTypes;

}
