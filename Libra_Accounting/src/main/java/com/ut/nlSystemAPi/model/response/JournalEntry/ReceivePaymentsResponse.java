package com.ut.nlSystemAPi.model.response.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentsResponse {

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 9)
    private Double exchangeRate;

    @ApiModelProperty(position = 15)
    private String chequeNumber;

    @ApiModelProperty(position = 15)
    private String invoice;

}
