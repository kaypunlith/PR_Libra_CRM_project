package com.ut.nlSystemAPi.model.request.Login.PurchaseBill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class PurchaseBillRequest {

    @ApiModelProperty(position = 2)
    private Long poType;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String pbCode;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 8)
    private Long paymentTermId;

    @ApiModelProperty(position = 8)
    private Long exchangeRateId;

    @ApiModelProperty(position = 8)
    private Long currencyCenterId;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String invoiceNo;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 12)
    private Long poNo;

    @ApiModelProperty(position = 15)
    private Long apId;

    @ApiModelProperty(position = 16)
    private Long shipmentId;

    @ApiModelProperty(position = 17)
    private String purchaseBillDate;

    @ApiModelProperty(position = 18)
    private String invoiceDate;

    @ApiModelProperty(position = 19)
    private Double subTotal;

    @ApiModelProperty(position = 20)
    private Double discountPercent;

    @ApiModelProperty(position = 20)
    private Double discountAmount;

    @ApiModelProperty(position = 21)
    private Long vatSettingId;

    @ApiModelProperty(position = 22)
    private Double totalVat;

    @ApiModelProperty(position = 23)
    private Double vatPercentage;

    @ApiModelProperty(position = 24)
    private List<PurchaseBillDetailRequest> details;

    @ApiModelProperty(position = 25)
    private Double depositAmount;

    @ApiModelProperty(position = 26)
    private List<PurchaseBillFileRequest> fileAttachment;

}
