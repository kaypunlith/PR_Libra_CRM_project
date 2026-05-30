package com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentBaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentDetailResponse extends ReceivePaymentBaseResponse {

    @ApiModelProperty(position = 1)
    private String invoiceCode;

    @ApiModelProperty(position = 1)
    private String memo;

    @ApiModelProperty(position = 1)
    private String receivedBy;

    @ApiModelProperty(position = 2)
    private Double totalAmount;

}
