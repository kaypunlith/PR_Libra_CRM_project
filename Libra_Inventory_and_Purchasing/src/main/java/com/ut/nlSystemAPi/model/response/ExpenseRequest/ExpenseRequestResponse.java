package com.ut.nlSystemAPi.model.response.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExpenseRequestResponse  {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 2)
    private String erDate;

    @ApiModelProperty(position = 3)
    private String erNumber;

    @ApiModelProperty(position = 4)
    private String refDocUrl;

    @ApiModelProperty(position = 4)
    private String refDocName;

    @ApiModelProperty(position = 5)
    private Long vendorId;

    @ApiModelProperty(position = 6)
    private String vendorName;

    @ApiModelProperty(position = 5)
    private Long vatId;

    @ApiModelProperty(position = 6)
    private String vatName;

    @ApiModelProperty(position = 7)
    private Double vatPercent;

    @ApiModelProperty(position = 7)
    private Long departmentId;

    @ApiModelProperty(position = 7)
    private String departmentName;

    @ApiModelProperty(position = 8)
    private String expectedErDate;

    @ApiModelProperty(position = 9)
    private Long purchaseId;

    @ApiModelProperty(position = 10)
    private String purchaseCode;

    @ApiModelProperty(position = 11)
    private Long purchaseType;

    @ApiModelProperty(position = 9)
    private Double totalAmount;

    @ApiModelProperty(position = 11)
    private Long isAllowApproval;

    @ApiModelProperty(position = 12)
    private String note;

    @ApiModelProperty(position = 12)
    private String approved;

    @ApiModelProperty(position = 12)
    private String approvedBy;

    @ApiModelProperty(position = 12)
    private String created;

    @ApiModelProperty(position = 12)
    private String modified;

    @ApiModelProperty(position = 13)
    private Long isCashAdvance;

    @ApiModelProperty(position = 13)
    private Long approveDepositAmount;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 6)
    private String currencySymbol;

    @ApiModelProperty(position = 15)
    private String modifiedBy;

    @ApiModelProperty(position = 15)
    private Long status;

    @ApiModelProperty(position = 15)
    private Long isClose;

    @ApiModelProperty(position = 14)
    private String closed;

    @ApiModelProperty(position = 14)
    private String closedBy;

    @ApiModelProperty(position = 15)
    private List<ExpenseRequestDetailResponse> details;

}
