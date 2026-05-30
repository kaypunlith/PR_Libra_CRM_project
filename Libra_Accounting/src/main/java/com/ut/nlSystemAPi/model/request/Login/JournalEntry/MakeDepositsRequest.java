package com.ut.nlSystemAPi.model.request.Login.JournalEntry;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MakeDepositsRequest {

    @ApiModelProperty(position = 1)
    private String date;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 3)
    private Long depositType;

    @ApiModelProperty(position = 4)
    private Long applyDepositToId;

    @ApiModelProperty(position = 5)
    private String applyReferenceCode;

    @ApiModelProperty(position = 6)
    private String applyReferenceName;

    @ApiModelProperty(position = 7)
    private Long chartAccountId;

    @ApiModelProperty(position = 8)
    private Double exchangeRate;

    @ApiModelProperty(position = 9)
    private Long isRecurrence;

    @ApiModelProperty(position = 10)
    private Long isOrVoucher;

    @ApiModelProperty(position = 11)
    private Long isPvVoucher;

    @ApiModelProperty(position = 12)
    private Long classId;

    @ApiModelProperty(position = 13)
    private Long companyId;

    @ApiModelProperty(position = 14)
    private Long branchId;

    @ApiModelProperty(position = 15)
    private String chequeNo;

    @ApiModelProperty(position = 16)
    private Long customerId;

    @ApiModelProperty(position = 17)
    private Long vendorId;

    @ApiModelProperty(position = 18)
    private Long employeeId;

    @ApiModelProperty(position = 19)
    private Double amount;

    @ApiModelProperty(position = 20)
    private String note;

    @ApiModelProperty(position = 21)
    private List<JournalEntryFileRequest> file;

    @ApiModelProperty(position = 22)
    private List<WriteChecksRequestDetailRequest> writeChecksRequestDetailRequests;


}
