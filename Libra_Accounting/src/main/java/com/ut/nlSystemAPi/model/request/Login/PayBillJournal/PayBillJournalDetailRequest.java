package com.ut.nlSystemAPi.model.request.Login.PayBillJournal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayBillJournalDetailRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 5)
    private Long vendorId;

    @ApiModelProperty(position = 4)
    private String reference;

    @ApiModelProperty(position = 5)
    private Long locationId;

    @ApiModelProperty(position = 5)
    private Long chartAccountId;

    @ApiModelProperty(position = 6)
    private Double totalAmount;

    @ApiModelProperty(position = 7)
    private Double amountDue;

    @ApiModelProperty(position = 8)
    private Double amountPaid;

    @ApiModelProperty(position = 9)
    private Double balance;

    @ApiModelProperty(position = 10)
    private String memo;

}
