package com.ut.nlSystemAPi.model.response.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class JournalEntryResponse {

    @ApiModelProperty(position = 1)
    private Long no;

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long applyToId;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 3)
    private String COA;

    @ApiModelProperty(position = 3)
    private String paidTo;

    @ApiModelProperty(position = 4)
    private String reference;

    @ApiModelProperty(position = 5)
    private Long adj;

    @ApiModelProperty(position = 5)
    private Long isRecurrence;

    @ApiModelProperty(position = 7)
    private String type;

    @ApiModelProperty(position = 9)
    private Double exchangeRate;

    @ApiModelProperty(position = 9)
    private Double amount;

    @ApiModelProperty(position = 9)
    private String chequeNo;

    @ApiModelProperty(position = 5)
    private Long isOrVoucher;

    @ApiModelProperty(position = 5)
    private Long isPvVoucher;

    @ApiModelProperty(position = 9)
    private String note;

    @ApiModelProperty(position = 8)
    private Long companyId;

    @ApiModelProperty(position = 9)
    private String companyName;

    @ApiModelProperty(position = 8)
    private Long chartAccountId;

    @ApiModelProperty(position = 9)
    private String chartAccountName;

    @ApiModelProperty(position = 10)
    private String description;

    @ApiModelProperty(position = 11)
    private Long classId;

    @ApiModelProperty(position = 12)
    private String className;

    @ApiModelProperty(position = 6)
    private String createdBy;

    @ApiModelProperty(position = 7)
    private String modifiedBy;

    @ApiModelProperty(position = 8)
    private Long status;

    @ApiModelProperty(position = 8)
    private Long depositType;

    @ApiModelProperty(position = 9)
    private Long branchId;

    @ApiModelProperty(position = 10)
    private String branchName;

    @ApiModelProperty(position = 10)
    private String paidToName;

    @ApiModelProperty(position = 11)
    private List<JournalEntryDetailResponse> journalEntryDetail;

    @ApiModelProperty(position = 12)
    private List<JournalEntryFileResponse> file;

    @ApiModelProperty(position = 13)
    private List<MakeDepositApplyToResponse> makeDepositApplyToResponses;

}
