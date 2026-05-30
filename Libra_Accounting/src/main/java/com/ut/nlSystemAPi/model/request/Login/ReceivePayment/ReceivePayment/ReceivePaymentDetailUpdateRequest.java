package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePayment;

import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentBaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentDetailUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private String reference;

    @ApiModelProperty(position = 5)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private Long customerContactId;

    @ApiModelProperty(position = 5)
    private Long employeeId;

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
