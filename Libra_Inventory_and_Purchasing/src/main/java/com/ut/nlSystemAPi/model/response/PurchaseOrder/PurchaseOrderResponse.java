package com.ut.nlSystemAPi.model.response.PurchaseOrder;

import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String poNo;

    @ApiModelProperty(position = 3)
    private String soNo;

    @ApiModelProperty(position = 3)
    private Long hasEr;

    @ApiModelProperty(position = 4)
    private String invoiceNo;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private String companyName;

    @ApiModelProperty(position = 7)
    private Long vendorId;

    @ApiModelProperty(position = 8)
    private String vendorName;

    @ApiModelProperty(position = 8)
    private String email;

    @ApiModelProperty(position = 9)
    private String poDate;

    @ApiModelProperty(position = 10)
    private Long vendorContactId;

    @ApiModelProperty(position = 11)
    private String vendorContactName;

    @ApiModelProperty(position = 12)
    private Long currencyCenterId;

    @ApiModelProperty(position = 13)
    private String currencyCenterName;

    @ApiModelProperty(position = 14)
    private VendorPhoto refDoc;

    @ApiModelProperty(position = 15)
    private String deliveryLeadTime;

    @ApiModelProperty(position = 16)
    private String note;

    @ApiModelProperty(position = 17)
    private String symbol;

    @ApiModelProperty(position = 18)
    private Long deliveryTo;

    @ApiModelProperty(position = 19)
    private String deliveryToName;

    @ApiModelProperty(position = 20)
    private String refSaleOrder;

    @ApiModelProperty(position = 21)
    private Long paymentTermId;

    @ApiModelProperty(position = 22)
    private String paymentTermName;

    @ApiModelProperty(position = 23)
    private Long shipmentId;

    @ApiModelProperty(position = 24)
    private String shipmentName;

    @ApiModelProperty(position = 25)
    private Long shipTo;

    @ApiModelProperty(position = 26)
    private String shipToName;

    @ApiModelProperty(position = 27)
    private Long contactShipTo;

    @ApiModelProperty(position = 28)
    private String contactShipToName;

    @ApiModelProperty(position = 29)
    private Long partOfDischargeId;

    @ApiModelProperty(position = 30)
    private String partOfDischargeName;

    @ApiModelProperty(position = 31)
    private Long partOfDischargeContactId;

    @ApiModelProperty(position = 32)
    private String partOfDischargeContactName;

    @ApiModelProperty(position = 33)
    private String expectedDeliveryDate;

    @ApiModelProperty(position = 34)
    private Double subTotal;

    @ApiModelProperty(position = 35)
    private Double totalAmount;

    @ApiModelProperty(position = 36)
    private Double totalDeposit;

    @ApiModelProperty(position = 37)
    private Long vatSettingId;

    @ApiModelProperty(position = 38)
    private String vatSettingName;

    @ApiModelProperty(position = 39)
    private Double totalVat;

    @ApiModelProperty(position = 40)
    private Double vatPercentage;

    @ApiModelProperty(position = 41)
    private Long isClose;

    @ApiModelProperty(position = 42)
    private Long status;

    @ApiModelProperty(position = 43)
    private String created;

    @ApiModelProperty(position = 44)
    private String createdBy;

    @ApiModelProperty(position = 45)
    private String modified;

    @ApiModelProperty(position = 46)
    private String modifiedBy;

    @ApiModelProperty(position = 47)
    private List<PurchaseOrderDetailResponse> details;

}
