package com.ut.nlSystemAPi.model.request.Login.PurchaseReceive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseReceiveSaveRequest {
    @ApiModelProperty(position = 1)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 2)
    private String dateReceiveApplyAll;

    @ApiModelProperty(position = 3)
    private List<PurchaseReceiveSaveDetailRequest> details;

}
