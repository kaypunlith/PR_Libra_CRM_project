package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentEmployee;

import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentOrganization.ReceivePaymentOrganizationDetailUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePaymentEmployeeDetailUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String date;

    @ApiModelProperty(position = 4)
    private String reference;

    @ApiModelProperty(position = 5)
    private Long chartAccountId;

    @ApiModelProperty(position = 5, hidden = true)
    private Long customerId;

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
