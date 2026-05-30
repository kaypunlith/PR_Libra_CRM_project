package com.ut.nlSystemAPi.model.request.Login.PostToJournal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostToJournalRequestDetails {

    @ApiModelProperty(position = 1, hidden = true)
    private Long generalLedgerId;
    @ApiModelProperty(position = 2)
    private Long chartAccountId;
    @ApiModelProperty(position = 3)
    private Double debit;
    @ApiModelProperty(position = 4)
    private Double credit;
    @ApiModelProperty(position = 5)
    private String memo;
}
