package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WriteChecksRequestUpdate {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 3)
    private Double exchangeRate;

    @ApiModelProperty(position = 4)
    private Long isRecurrence;

    @ApiModelProperty(position = 5)
    private Long isOrVoucher;

    @ApiModelProperty(position = 6)
    private Long isPvVoucher;

    @ApiModelProperty(position = 7)
    private Long classId;

    @ApiModelProperty(position = 8)
    private Long companyId;

    @ApiModelProperty(position = 8)
    private Long branchId;


    @ApiModelProperty(position = 9)
    private String chequeNo;

    @ApiModelProperty(position = 10)
    private Long customerId;

    @ApiModelProperty(position = 11)
    private Long vendorId;

    @ApiModelProperty(position = 12)
    private Long employeeId;

    @ApiModelProperty(position = 13)
    private Double amount;

    @ApiModelProperty(position = 14)
    private String note;

    @ApiModelProperty(position = 20)
    private List<JournalEntryFileRequest> file;

    @ApiModelProperty(position = 23)
    private List<WriteChecksUpdateDetailRequest> writeChecksRequestDetailRequests;


}
