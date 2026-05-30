package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseBill extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String poCode;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 6)
    private Long poType;

    @ApiModelProperty(position = 7)
    private Long paymentTermId;

    @ApiModelProperty(position = 8)
    private Long warehouseId;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String invoiceNo;

    @ApiModelProperty(position = 11)
    private String note;

    @ApiModelProperty(position = 12)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 13)
    private Long pvRequestId;

    @ApiModelProperty(position = 14)
    private Long apId;

    @ApiModelProperty(position = 14)
    private Long exchangeRateId;

    @ApiModelProperty(position = 14)
    private Long currencyCenterId;

    @ApiModelProperty(position = 15)
    private Long shipmentId;

    @ApiModelProperty(position = 16)
    private String purchaseBillDate;

    @ApiModelProperty(position = 17)
    private String invoiceDate;

    @ApiModelProperty(position = 18)
    private Double subTotal;

    @ApiModelProperty(position = 19)
    private Double discountPercent;

    @ApiModelProperty(position = 19)
    private Double discountAmount;

    @ApiModelProperty(position = 20)
    private Long vatSettingId;

    @ApiModelProperty(position = 20)
    private Long vatChartAccountId;

    @ApiModelProperty(position = 20)
    private Long vatCalculate;

    @ApiModelProperty(position = 21)
    private Double balance;

    @ApiModelProperty(position = 21)
    private Double totalVat;

    @ApiModelProperty(position = 22)
    private Double vatPercentage;

    @ApiModelProperty(position = 23)
    private Integer isClose;

    @ApiModelProperty(position = 24)
    private Double depositAmount;
}
