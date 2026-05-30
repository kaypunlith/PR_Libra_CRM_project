package com.ut.nlSystemAPi.model.response.PurchaseReceive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseReceiveResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 2)
    private String purchaseBillNo;

    @ApiModelProperty(position = 3)
    private String purchaseBillDate;

    @ApiModelProperty(position = 14)
    private String invoiceNo;

    @ApiModelProperty(position = 15)
    private String invoiceDate;

    @ApiModelProperty(position = 3)
    private String poNo;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private String vendorName;

    @ApiModelProperty(position = 6)
    private Long locationId;

    @ApiModelProperty(position = 7)
    private String locationName;

    @ApiModelProperty(position = 9)
    private Long status;

    @ApiModelProperty(position = 10)
    private Long warehouseId;

    @ApiModelProperty(position = 11)
    private String warehouseName;

    @ApiModelProperty(position = 12)
    private String purchaseReceiveDate;

    @ApiModelProperty(position = 13)
    private String purchaseReceiveCode;

    @ApiModelProperty(position = 14)
    private Long companyId;

    @ApiModelProperty(position = 15)
    private List<PurchaseReceiveDetailResponse> purchaseReceiveDetailResponse;


}
