package com.ut.nlSystemAPi.model.request.Login.ReceivePayment.ReceivePaymentEmployee;

import com.ut.nlSystemAPi.model.request.Login.JournalEntry.JournalEntryFileRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePaymentEmployeeUpdateRequest {

    @ApiModelProperty(position = 1, hidden = true)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private Long branchId;

    @ApiModelProperty(position = 2)
    private String date;

    @ApiModelProperty(position = 3)
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
    private List<ReceivePaymentEmployeeDetailUpdateRequest> receivePaymentEmployeeDetail;

    @ApiModelProperty(position = 20)
    private List<JournalEntryFileRequest> files;

}
