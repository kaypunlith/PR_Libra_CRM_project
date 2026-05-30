package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrder extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String prCode;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private String orderDate;

    @ApiModelProperty(position = 5)
    private String deliveryLeadTime;

    @ApiModelProperty(position = 6)
    private Long vendorContactId;

    @ApiModelProperty(position = 7)
    private Long paymentId;

    @ApiModelProperty(position = 8)
    private Long exchangeRateId;

    @ApiModelProperty(position = 8)
    private Long currencyCenterId;

    @ApiModelProperty(position = 9)
    private String refDoc;

    @ApiModelProperty(position = 9)
    private String refDocName;

    @ApiModelProperty(position = 10)
    private String note;

    @ApiModelProperty(position = 11)
    private Long finalPlaceDeliveryId;

    @ApiModelProperty(position = 12)
    private String refQuotation;

    @ApiModelProperty(position = 13)
    private Long partOfDischargeId;

    @ApiModelProperty(position = 14)
    private Long shipmentId;

    @ApiModelProperty(position = 15)
    private Long partOfDischargeContactId;

    @ApiModelProperty(position = 16)
    private String expectedDeliveryDate;

    @ApiModelProperty(position = 17)
    private Long shipTo;

    @ApiModelProperty(position = 18)
    private Long contactShipTo;

    @ApiModelProperty(position = 19)
    private Double totalAmount;

    @ApiModelProperty(position = 20)
    private Long vatSettingId;

    @ApiModelProperty(position = 21)
    private Double totalVat;

    @ApiModelProperty(position = 22)
    private Double vatPercentage;

    @ApiModelProperty(position = 23)
    private Integer isClose;

}
