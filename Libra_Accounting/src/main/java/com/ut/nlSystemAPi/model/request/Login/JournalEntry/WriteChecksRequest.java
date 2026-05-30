package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WriteChecksRequest {

    @ApiModelProperty(position = 1)
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

    @ApiModelProperty(position = 9)
    private Long branchId;


    @ApiModelProperty(position = 10)
    private String chequeNo;

    @ApiModelProperty(position = 11)
    private Long customerId;

    @ApiModelProperty(position = 12)
    private Long vendorId;

    @ApiModelProperty(position = 13)
    private Long employeeId;

    @ApiModelProperty(position = 14)
    private Double amount;

    @ApiModelProperty(position = 15)
    private String note;

    @ApiModelProperty(position = 16)
    private List<JournalEntryFileRequest> file;

    @ApiModelProperty(position = 23)
    private List<WriteChecksRequestDetailRequest> writeChecksRequestDetailRequests;


}
