package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePayment;

import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentRequest extends ReceivePaymentBaseRequest {

    @ApiModelProperty(position = 1)
    private String memo;

    @ApiModelProperty(position = 2)
    private String organization;

}
