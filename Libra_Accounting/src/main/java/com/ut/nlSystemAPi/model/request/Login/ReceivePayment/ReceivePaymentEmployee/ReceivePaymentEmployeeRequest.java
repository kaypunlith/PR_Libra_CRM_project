package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentEmployee;

import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentEmployeeRequest extends ReceivePaymentBaseRequest {

    @ApiModelProperty(position = 1)
    private String employeeName;

}
