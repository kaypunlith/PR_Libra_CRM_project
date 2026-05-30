package com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePayment;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentBaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePaymentResponse extends ReceivePaymentBaseResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long glId;

    @ApiModelProperty(position = 2)
    private Long isVat;

    @ApiModelProperty(position = 2)
    private String companyName;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String invoiceCode;

    @ApiModelProperty(position = 3)
    private String reference;

    @ApiModelProperty(position = 4)
    private String memo;

    @ApiModelProperty(position = 5)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private Long customerContactId;

    @ApiModelProperty(position = 5)
    private String customerTel;

    @ApiModelProperty(position = 5)
    private String customerAddress;

    @ApiModelProperty(position = 5)
    private String customerName;

    @ApiModelProperty(position = 5)
    private String employeeName;

    @ApiModelProperty(position = 5)
    private Long paymentTermId;

    @ApiModelProperty(position = 7)
    private Double totalAmount;

    @ApiModelProperty(position = 8)
    private Double amountDue;

    @ApiModelProperty(position = 9)
    private Double balance;

    @ApiModelProperty(position = 10)
    private String nextExpired;

    @ApiModelProperty(position = 10)
    private String chartAccountName;

    @ApiModelProperty(position = 12)
    private String note;

    @ApiModelProperty(position = 12)
    private String aging;

    @ApiModelProperty(position = 13)
    private Double exchangeRate;

    @ApiModelProperty(position = 14)
    private String chequeNumber;

    @ApiModelProperty(position = 15)
    private String accountCode;

    @ApiModelProperty(position = 15)
    private String soCode;

    @ApiModelProperty(position = 15)
    private String attn;

    @ApiModelProperty(position = 16)
    private Long branchId;

    @ApiModelProperty(position = 17)
    private String branchName;

    @ApiModelProperty(position = 18)
    private List<ReceivePaymentFileResponse> files;

    @ApiModelProperty(position = 19)
    private List<ReceivePaymentDetailResponse> detailResponses;

}
