package com.ut.nlSystemAPi.model.response.Report;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckReportResponse {

    @ApiModelProperty(position = 1)
    private Long no;

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 4)
    private String branchName;

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 6)
    private String adjustName;

    @ApiModelProperty(position = 7)
    private String name;

    @ApiModelProperty(position = 8)
    private String account;

    @ApiModelProperty(position = 9)
    private Double debit;

    @ApiModelProperty(position = 10)
    private Double credit;

    @ApiModelProperty(position = 11)
    private String companyName;

    @ApiModelProperty(position = 12)
    private Double totalDebit;

    @ApiModelProperty(position = 13)
    private Double totalCredit;

}
