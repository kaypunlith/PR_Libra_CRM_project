package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseBillBarcodeResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String poCode;

    @ApiModelProperty(position = 3)
    private String vendorName;

    @ApiModelProperty(position = 3)
    private Long status;

    @ApiModelProperty(position = 4)
    private List<PurchaseBillBarcodeDetailResponse> purchaseBillBarcodeDetailResponseList;

}
