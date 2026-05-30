package com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentBaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePaymentCreditStatementResponse extends ReceivePaymentBaseResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private String customerName;

    @ApiModelProperty(position = 4)
    private String customerAddress;

    @ApiModelProperty(position = 5)
    private String customerPhoneNumber;

    @ApiModelProperty(position = 6)
    private String customerAtt;

    @ApiModelProperty(position = 7)
    private String date;

    @ApiModelProperty(position = 8)
    private String bankName;

    @ApiModelProperty(position = 9)
    private String accountName;

    @ApiModelProperty(position = 10)
    private String accountNumber;

    @ApiModelProperty(position = 11)
    private String prepareBy;

    @ApiModelProperty(position = 12)
    private String prepareByTel;

    @ApiModelProperty(position = 13)
    private String prepareByGmail;

    @ApiModelProperty(position = 15)
    private List<ReceivePaymentResponse> receivePaymentResponsesList;

}
