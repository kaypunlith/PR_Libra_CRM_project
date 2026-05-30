package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentOrganization;

import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePaymentOrganizationUpdateRequest {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 6)
    private Long chartAccountId;

    @ApiModelProperty(position = 7)
    private String note;

    @ApiModelProperty(position = 8)
    private String chequeNumber;

    @ApiModelProperty(position = 8)
    private Double exchangeRate;

    @ApiModelProperty(position = 18)
    private List<ReceivePaymentOrganizationDetailUpdateRequest> receivePaymentOrganizationDetail;

    @ApiModelProperty(position = 19)
    private List<JournalEntryFileRequest> files;

}
