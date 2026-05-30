package com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentOrganization;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentBaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentOrganizationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
    private String reference;

    @ApiModelProperty(position = 5)
    private Long customerId;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private Long chartAccountId;

    @ApiModelProperty(position = 6)
    private String chartAccount;

    @ApiModelProperty(position = 6)
    private String customerName;

    @ApiModelProperty(position = 5)
    private Long paymentTermId;

    @ApiModelProperty(position = 7)
    private Double totalAmount;

    @ApiModelProperty(position = 8)
    private Double amountDue;

    @ApiModelProperty(position = 9)
    private Double balance;


    @ApiModelProperty(position = 9)
    private Long branchId;


    @ApiModelProperty(position = 9)
    private String branchName;

    @ApiModelProperty(position = 6)
    private String memo;

    @ApiModelProperty(position = 6)
    private String note;

    @ApiModelProperty(position = 10)
    private String nextExpired;

    @ApiModelProperty(position = 10)
    private String createdBy;

    @ApiModelProperty(position = 11)
    private String createdDate;

    @ApiModelProperty(position = 12)
    private String modifiedBy;

    @ApiModelProperty(position = 13)
    private String modifiedDate;

}
