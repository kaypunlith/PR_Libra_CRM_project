package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePayment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentFileRequest {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String url;

}
