package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReportDeposit implements Serializable {

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
    private Float totalAmountDeposit;

    @ApiModelProperty(position = 8)
    private String dateRequest;

    @ApiModelProperty(position = 9)
    private Long checkDateRequest;

    @ApiModelProperty(position = 10)
    private Float adjustBeginningAmount;

    @ApiModelProperty(position = 11)
    private String adjustBeginningDate;

    @ApiModelProperty(position = 12)
    private List<ReportDepositMonthly> monthlyDeposit;

    @ApiModelProperty(position = 13)
    private List<ReportDepositRequest> monthlyDepositRequest;

}
