package com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentBaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentFileResponse extends ReceivePaymentBaseResponse {

    @ApiModelProperty(position = 1)
    private String fileName;

    @ApiModelProperty(position = 2)
    private String fileUrl;

}
