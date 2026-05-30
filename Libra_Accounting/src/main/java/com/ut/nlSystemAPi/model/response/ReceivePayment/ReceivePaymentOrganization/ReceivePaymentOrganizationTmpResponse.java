package com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentOrganization;

import com.ut.nlSystemAPi.model.response.ReceivePayment.ReceivePaymentBaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReceivePaymentOrganizationTmpResponse extends ReceivePaymentBaseResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long mainGlId;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 5)
    private Long customerId;

    @ApiModelProperty(position = 6)
    private Long companyId;

    @ApiModelProperty(position = 5)
    private Long paymentTermId;

    @ApiModelProperty(position = 8)
    private Double credit;

}
