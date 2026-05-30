package com.ut.nlSystemAPi.model.response.PurchaseBill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseBillResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String purchaseBillNo;

    @ApiModelProperty(position = 2)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 2)
    private String poNo;

    @ApiModelProperty(position = 2)
    private String erNumber;

    @ApiModelProperty(position = 2)
    private Long erStatus;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String companyName;

    @ApiModelProperty(position = 5)
    private Long vendorId;

    @ApiModelProperty(position = 6)
    private String vendorName;

    @ApiModelProperty(position = 7)
    private Long locationId;

    @ApiModelProperty(position = 8)
    private String locationName;

    @ApiModelProperty(position = 9)
    private Long warehouseId;

    @ApiModelProperty(position = 10)
    private String warehouseName;

    @ApiModelProperty(position = 11)
    private String purchaseBillDate;

    @ApiModelProperty(position = 12)
    private Long apId;

    @ApiModelProperty(position = 13)
    private String apName;

    @ApiModelProperty(position = 14)
    private String invoiceNo;

    @ApiModelProperty(position = 15)
    private String invoiceDate;

    @ApiModelProperty(position = 16)
    private String note;

    @ApiModelProperty(position = 17)
    private Long paymentTermId;

    @ApiModelProperty(position = 18)
    private String paymentTermName;

    @ApiModelProperty(position = 18)
    private Long paymentTypeId;

    @ApiModelProperty(position = 18)
    private String paymentTypeName;

    @ApiModelProperty(position = 19)
    private Long shipmentId;

    @ApiModelProperty(position = 20)
    private String shipmentName;

    @ApiModelProperty(position = 21)
    private Double subTotal;

    @ApiModelProperty(position = 22)
    private Double discountAmount;

    @ApiModelProperty(position = 22)
    private Double discountPercent;

    @ApiModelProperty(position = 23)
    private Long vatSettingId;

    @ApiModelProperty(position = 23)
    private String vatSettingName;

    @ApiModelProperty(position = 24)
    private Double totalVat;

    @ApiModelProperty(position = 25)
    private Double vatPercentage;

    @ApiModelProperty(position = 25)
    private Double totalAmount;

    @ApiModelProperty(position = 25)
    private Double balance;

    @ApiModelProperty(position = 26)
    private Long status;

    @ApiModelProperty(position = 27)
    private Long poType;

    @ApiModelProperty(position = 28)
    private String created;

    @ApiModelProperty(position = 29)
    private String createdBy;

    @ApiModelProperty(position = 30)
    private String modified;

    @ApiModelProperty(position = 31)
    private String modifiedBy;

    @ApiModelProperty(position = 31)
    private Long isApproved;

    @ApiModelProperty(position = 30)
    private String approved;

    @ApiModelProperty(position = 31)
    private String approvedBy;

    @ApiModelProperty(position = 32)
    private List<PurchaseBillDetailResponse> details;

    @ApiModelProperty(position = 33)
    private Double depositAmount;

    @ApiModelProperty(position = 34)
    private List<PurchaseBillFileResponse> fileAttachment;

}
