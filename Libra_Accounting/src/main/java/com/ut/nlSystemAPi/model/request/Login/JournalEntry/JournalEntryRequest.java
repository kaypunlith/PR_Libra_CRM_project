package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class JournalEntryRequest {

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Long branchId;

    @ApiModelProperty(position = 5)
    private Long isRecurrence;

    @ApiModelProperty(position = 6)
    private Long isOrVoucher;

    @ApiModelProperty(position = 7)
    private Long isPvVoucher;

    @ApiModelProperty(position = 8)
    private Long adj;

    @ApiModelProperty(position = 9)
    private Double exchangeRate;

    @ApiModelProperty(position = 10)
    private String chequeNo;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 15)
    private List<JournalEntryFileRequest> file;

    @ApiModelProperty(position = 11)
    private List<JournalEntryDetailRequest> journalEntryDetailRequests;


}
