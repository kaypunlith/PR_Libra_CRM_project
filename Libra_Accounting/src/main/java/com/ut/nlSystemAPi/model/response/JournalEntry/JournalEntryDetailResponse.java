package com.ut.nlSystemAPi.model.response.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class JournalEntryDetailResponse {

    @ApiModelProperty(position = 1)
    private Long no;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long journalEntryId;

    @ApiModelProperty(position = 3)
    private Long receiveDepositFromId;

    @ApiModelProperty(position = 3)
    private String receiveDepositFromName;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 6)
    private Long adj;

    @ApiModelProperty(position = 6)
    private Long isSys;

    @ApiModelProperty(position = 7)
    private Long isRecurrence;

    @ApiModelProperty(position = 8)
    private String type;

    @ApiModelProperty(position = 9)
    private Long companyId;

    @ApiModelProperty(position = 10)
    private String companyName;

    @ApiModelProperty(position = 11)
    private Long chartAccountTypeId;

    @ApiModelProperty(position = 11)
    private Long chartAccountId;

    @ApiModelProperty(position = 12)
    private String chartAccountName;

    @ApiModelProperty(position = 13)
    private String description;

    @ApiModelProperty(position = 14)
    private Long classId;

    @ApiModelProperty(position = 15)
    private String className;

    @ApiModelProperty(position = 16)
    private Double debit;

    @ApiModelProperty(position = 17)
    private Double credit;

    @ApiModelProperty(position = 17)
    private Double balance;

    // Signed amount from detail row (debit positive, credit negative)
    @ApiModelProperty(position = 18)
    private Double signedAmount;

    @ApiModelProperty(position = 18)
    private Double amount;

    // Calculated running balance per detail list when filtered by account group
    @ApiModelProperty(position = 19)
    private Double calculatedBalance;

    @ApiModelProperty(position = 18)
    private Long customerId;

    @ApiModelProperty(position = 19)
    private String customerName;

    @ApiModelProperty(position = 20)
    private Long vendorId;

    @ApiModelProperty(position = 21)
    private String vendorName;

    @ApiModelProperty(position = 22)
    private Long employeeId;

    @ApiModelProperty(position = 23)
    private String employeeName;

    @ApiModelProperty(position = 24)
    private String note;

    @ApiModelProperty(position = 24)
    private String invoice;

    @ApiModelProperty(position = 25)
    private String listType;

    @ApiModelProperty(position = 26)
    private String createdBy;

    @ApiModelProperty(position = 27)
    private String modifiedBy;

    @ApiModelProperty(position = 28)
    private Long status;

    @ApiModelProperty(position = 9)
    private Long branchId;

    @ApiModelProperty(position = 10)
    private String branchName;

    @ApiModelProperty(position = 9)
    private Double exchangeRate;

    @ApiModelProperty(position = 9)
    private String chequeNo;

    @ApiModelProperty(position = 5)
    private Long isOrVoucher;

    @ApiModelProperty(position = 5)
    private Long isPvVoucher;

    @ApiModelProperty(position = 30)
    private List<JournalEntryFileResponse> file;

}
