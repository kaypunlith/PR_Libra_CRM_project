package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MakeDepositsRequestDetailRequest {

    @ApiModelProperty(position = 8)
    private Long chartAccountId;

    @ApiModelProperty(position = 13)
    private Double amount;

    @ApiModelProperty(position = 9)
    private String memo;

    @ApiModelProperty(position = 17)
    private Long customerId;

    @ApiModelProperty(position = 17)
    private Long vendorId;

    @ApiModelProperty(position = 17)
    private Long employeeId;

    @ApiModelProperty(position = 11)
    private Long classId;
}
