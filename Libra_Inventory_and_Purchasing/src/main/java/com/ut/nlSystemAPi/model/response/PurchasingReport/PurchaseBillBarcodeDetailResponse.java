package com.ut.nlSystemAPi.model.response.PurchasingReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseBillBarcodeDetailResponse {
    @ApiModelProperty(position = 1)
    private String productCode;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 4)
    private String qty;

    @ApiModelProperty(position = 5)
    private String uom;

}
