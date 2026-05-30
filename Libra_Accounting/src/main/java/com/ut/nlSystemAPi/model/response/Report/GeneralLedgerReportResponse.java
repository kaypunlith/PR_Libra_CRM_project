package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeneralLedgerReportResponse {
    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 1)
    private Long generalLedgerId;
    @ApiModelProperty(position = 2)
    private String date;
    @ApiModelProperty(position = 3)
    private String branchName;
    @ApiModelProperty(position = 4)
    private Long branchId;
    @ApiModelProperty(position = 5)
    private  String createdBy;
    @ApiModelProperty(position = 6)
    private String reference;
    @ApiModelProperty(position = 6)
    private String receivePaymentCode;
    @ApiModelProperty(position = 7)
    private Long addJust;
    @ApiModelProperty(position = 8)
    private String type;
    @ApiModelProperty(position = 9)
    private String  accountCode;
    @ApiModelProperty(position = 9)
    private String  account;
    @ApiModelProperty(position = 10)
    private String  accountDescription;
    @ApiModelProperty(position = 11)
    private  String className;
    @ApiModelProperty(position = 12)
    private Double debit;
    @ApiModelProperty(position = 13)
    private Double credit;
    @ApiModelProperty(position = 15)
    private String description;

    @ApiModelProperty(position = 16)
    private Double beginBalance;

    @ApiModelProperty(position = 16)
    private Double balance;

    @ApiModelProperty(position = 16)
    private Long chartAccountId;

    @ApiModelProperty(position = 16)
    private List<GeneralLedgerReportResponse> detailedReports;


}
