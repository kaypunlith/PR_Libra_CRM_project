package com.ut.nlSystemAPi.model.request.Login.ReceivePayment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentBaseRequest {

//  this model not yet use to impl
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String reference;

    @ApiModelProperty(position = 5)
    private String memo;

    @ApiModelProperty(position = 6)
    private String organization;

    @ApiModelProperty(position = 7)
    private String totalAmount;

    @ApiModelProperty(position = 8)
    private String amountDue;

}
