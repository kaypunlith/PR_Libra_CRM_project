package com.ut.nlSystemAPi.model.response.DashboradAccounting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseTopVendorResponse {
    @ApiModelProperty(position = 1)
    private  Long vendorId;

    @ApiModelProperty(position = 2)
    private String vendorName;

    @ApiModelProperty(position = 2)
    private Double totalInvoice;

    @ApiModelProperty(position = 1)
    private  Double totalAmount;

}
