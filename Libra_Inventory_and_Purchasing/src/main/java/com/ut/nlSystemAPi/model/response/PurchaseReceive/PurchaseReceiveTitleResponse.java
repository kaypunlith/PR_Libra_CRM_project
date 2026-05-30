package com.ut.nlSystemAPi.model.response.PurchaseReceive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseReceiveTitleResponse {
    @ApiModelProperty(position = 12)
    private String purchaseReceiveDate;

    @ApiModelProperty(position = 13)
    private String purchaseReceiveCode;

    @ApiModelProperty(position = 14)
    private List<PurchaseReceiveDetailResponse> purchaseReceiveDetailResponse;


}