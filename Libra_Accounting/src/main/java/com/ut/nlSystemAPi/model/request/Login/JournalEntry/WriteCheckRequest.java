package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WriteCheckRequest {

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private Long isRecurrence;

    @ApiModelProperty(position = 5)
    private Long isOrVoucher;

    @ApiModelProperty(position = 5)
    private Long isPvVoucher;

    @ApiModelProperty(position = 7)
    private Long adj;

    @ApiModelProperty(position = 8)
    private Double exchangeRate;

    @ApiModelProperty(position = 9)
    private String chequeNo;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 15)
    private List<JournalEntryFileRequest> file;

    @ApiModelProperty(position = 11)
    private List<JournalEntryDetailRequest> journalEntryDetailRequests;


}
