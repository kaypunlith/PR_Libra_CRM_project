package com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoodReceiptNoteRequest{

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long locationGroupId;

    @ApiModelProperty(position = 4)
    private Long locationId;

    @ApiModelProperty(position = 6)
    private Long vendorId;

    @ApiModelProperty(position =7)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 8)
    private String date;

    @ApiModelProperty(position = 9)
    private String note;

    @ApiModelProperty(position = 10)
    private List<GoodReceiptNoteDetailRequest> goodReceiptNoteDetailRequests;
}
