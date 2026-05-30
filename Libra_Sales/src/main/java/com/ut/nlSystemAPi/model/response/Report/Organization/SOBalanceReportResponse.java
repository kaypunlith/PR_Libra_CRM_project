package com.ut.nlSystemAPi.model.response.Report.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SOBalanceReportResponse {

    @ApiModelProperty(position = 1)
    private Long organizationId;

    @ApiModelProperty(position = 2)
    private String organizationName;

    @ApiModelProperty(position = 5)
    private Long soId;

    @ApiModelProperty(position = 6)
    private String soNo;

    @ApiModelProperty(position = 7)
    private String soDate;

    @ApiModelProperty(position = 8)
    private Long invoiceId;

    @ApiModelProperty(position = 9)
    private String invoiceNo;

    @ApiModelProperty(position = 10)
    private String createdBy;

    @ApiModelProperty(position = 11)
    private String currencySymbol;

    @ApiModelProperty(position = 12)
    private Double totalAmount;

    @ApiModelProperty(position = 13)
    private String soStatus;

    @ApiModelProperty(position = 14)
    private Integer soStatusColor;

    @ApiModelProperty(position = 15)
    private Integer status;

    @ApiModelProperty(position = 16)
    private Integer type;

    @ApiModelProperty(position = 17)
    private Integer isClose;

    @ApiModelProperty(position = 18)
    private Integer isApprove;

    @ApiModelProperty(position = 19)
    private Long dateDiff;

    @ApiModelProperty(position = 17)
    private List<SOBalanceReportResponse> details;

}