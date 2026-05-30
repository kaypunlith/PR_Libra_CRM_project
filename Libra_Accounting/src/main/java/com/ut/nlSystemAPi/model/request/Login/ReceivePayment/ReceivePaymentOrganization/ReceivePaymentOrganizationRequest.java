package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentOrganization;

import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentOrganizationRequest extends ReceivePaymentBaseRequest {

    @ApiModelProperty(position = 1)
    private String organization;

}
