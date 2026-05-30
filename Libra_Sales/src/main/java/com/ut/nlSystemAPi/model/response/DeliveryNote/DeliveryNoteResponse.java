package com.ut.nlSystemAPi.model.response.DeliveryNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeliveryNoteResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 1)
    private String companyName;

    @ApiModelProperty(position = 1)
    private String companyEmail;

    @ApiModelProperty(position = 1)
    private String companyTelephone;

    @ApiModelProperty(position = 1)
    private String companyAddress;

    @ApiModelProperty(position = 1)
    private String companyWebsite;

    @ApiModelProperty(position = 2)
    private String deliveryCode;

    @ApiModelProperty(position = 3)
    private String shipTo;

    @ApiModelProperty(position = 4)
    private String note;

    @ApiModelProperty(position = 5)
    private String deliveryDate;

    @ApiModelProperty(position = 6)
    private Long saleOrderId;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 8)
    private String warehouseName;

    @ApiModelProperty(position = 7)
    private Long customerContactId;

    @ApiModelProperty(position = 8)
    private String customerContactName;

    @ApiModelProperty(position = 9)
    private String invoiceCode;

    @ApiModelProperty(position = 10)
    private String invoiceDate;

    @ApiModelProperty(position = 11)
    private String saleOrderCode;

    @ApiModelProperty(position = 12)
    private String expectedDeliveryDate;

    @ApiModelProperty(position = 13)
    private Long expectedDeliveryDateDiff;

    @ApiModelProperty(position = 14)
    private Long organizationId;

    @ApiModelProperty(position = 15)
    private String organizationCode;

    @ApiModelProperty(position = 16)
    private String organizationName;

    @ApiModelProperty(position = 16)
    private String customerContact;

    @ApiModelProperty(position = 16)
    private String customerContactTelephone;

    @ApiModelProperty(position = 16)
    private String customerAddress;

    @ApiModelProperty(position = 17)
    private Integer status;

    @ApiModelProperty(position = 17)
    private Double totalVat;

    @ApiModelProperty(position = 17)
    private Integer isNoneVat;

    @ApiModelProperty(position = 24)
    private Long vatId;

    @ApiModelProperty(position = 25)
    private String vatName;

    @ApiModelProperty(position = 26)
    private Double vatExchangeRate;

    @ApiModelProperty(position = 27)
    private Double vatPercent;

    @ApiModelProperty(position = 22)
    private Double subTotal;

    @ApiModelProperty(position = 23)
    private Double totalAmount;

    @ApiModelProperty(position = 22)
    private Double discountAmount;

    @ApiModelProperty(position = 23)
    private Double discountPercent;

    @ApiModelProperty(position = 18)
    private String created;

    @ApiModelProperty(position = 19)
    private String createdBy;

    @ApiModelProperty(position = 20)
    private List<DeliveryNoteDetailResponse> details;
}
