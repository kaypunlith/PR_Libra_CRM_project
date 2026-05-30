package com.ut.nlSystemAPi.model.request.SaleInvoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaleInvoiceRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Integer isUpdate;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String moduleCode;

    @ApiModelProperty(position = 3)
    private Long quotationId;

    @ApiModelProperty(position = 3)
    private String quotationNumber;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String invoiceDate;

    @ApiModelProperty(position = 5)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private Long paymentTermId;

    @ApiModelProperty(position = 7)
    private Long chartAccountId;

    @ApiModelProperty(position = 7)
    private Long saleRepId;

    @ApiModelProperty(position = 3)
    private Long saleOrderId;

    @ApiModelProperty(position = 3)
    private String saleOrderNumber;

    @ApiModelProperty(position = 3)
    private Long transferOrderId;

    @ApiModelProperty(position = 3)
    private String transferOrderNumber;

    @ApiModelProperty(position = 3)
    private Long bomId;

    @ApiModelProperty(position = 3)
    private String bomCode;

    @ApiModelProperty(position = 7)
    private Long priceTypeId;

    @ApiModelProperty(position = 7)
    private Long departmentId;

    @ApiModelProperty(position = 7)
    private Long vatSettingId;

    @ApiModelProperty(position = 10)
    private Long organizationContactId;

    @ApiModelProperty(position = 10)
    private String organizationPoNo;

    @ApiModelProperty(position = 10)
    private String deliveryDate;

    @ApiModelProperty(position = 12)
    private Double vatPercent;

    @ApiModelProperty(position = 12)
    private Double totalVat;

    @ApiModelProperty(position = 12)
    private Double totalAmount;

    @ApiModelProperty(position = 12)
    private Double discountAmount;

    @ApiModelProperty(position = 12)
    private Double discountPercent;

    @ApiModelProperty(position = 12)
    private Double subTotal;

    @ApiModelProperty(position = 21)
    private String note;

    @ApiModelProperty(position = 17)
    private Integer recurrence;

    @ApiModelProperty(position = 17)
    private Integer isPos;

    @ApiModelProperty(position = 17)
    private Integer isApply;

    @ApiModelProperty(position = 17)
    private Integer isDeposit;

    @ApiModelProperty(position = 18)
    private List<Long> invoiceDeposits;

    @ApiModelProperty(position = 23)
    private Integer isNoneVat;

    @ApiModelProperty(position = 26)
    private List<SaleInvoiceTermConditionRequest> termConditions;

    @ApiModelProperty(position = 26)
    private List<SaleInvoiceDetailRequest> details;




    // POS
    @ApiModelProperty(position = 30)
    private Long exchangeRateId;

    @ApiModelProperty(position = 31)
    private Long currencyCenterId;

    @ApiModelProperty(position = 32)
    private Double paidAmount;

    @ApiModelProperty(position = 33)
    private Double paidKhr;

    @ApiModelProperty(position = 34)
    private Double balance;

    @ApiModelProperty(position = 35)
    private Double changeAmount;

    @ApiModelProperty(position = 36)
    private Double changeAmountKhr;
}
