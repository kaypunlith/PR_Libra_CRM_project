package com.ut.nlSystemAPi.model.request.Login.PayBills;

import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentOrganization.ReceivePaymentOrganizationDetailUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayBillRequest {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 3)
    private Long locationId;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 6)
    private Long chartAccountId;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 8)
    private String chequeNumber;

    @ApiModelProperty(position = 8)
    private String bankNo;

    @ApiModelProperty(position = 8)
    private Double exchangeRate;

    @ApiModelProperty(position = 14)
    private List<JournalEntryFileRequest> payBillFileRequests;

    @ApiModelProperty(position = 15)
    private List<PayBillDetailRequest> payBillDetailRequests;

}
