package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class JournalEntryDetailRequest {

    @ApiModelProperty(position = 8)
    private Long chartAccountId;

    @ApiModelProperty(position = 13)
    private Double debit;

    @ApiModelProperty(position = 14)
    private Double credit;

    @ApiModelProperty(position = 9)
    private String memo;

    @ApiModelProperty(position = 17)
    private Long customerId;

    @ApiModelProperty(position = 17)
    private Long vendorId;

    @ApiModelProperty(position = 17)
    private Long employeeId;

    @ApiModelProperty(position = 18)
    private Long classId;
}
