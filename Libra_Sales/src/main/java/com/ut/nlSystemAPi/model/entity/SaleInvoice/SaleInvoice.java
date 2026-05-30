package com.ut.nlSystemAPi.model.entity.SaleInvoice;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleInvoice extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private Long companyId;

    @ApiModelProperty(position = 4)
    private String invoiceDate;

    @ApiModelProperty(position = 3)
    private Long quotationId;

    @ApiModelProperty(position = 3)
    private String quotationNumber;

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
    private Long warehouseId;

    @ApiModelProperty(position = 7)
    private Long paymentTermId;

    @ApiModelProperty(position = 7)
    private Long chartAccountId;

    @ApiModelProperty(position = 5)
    private Long organizationId;

    @ApiModelProperty(position = 7)
    private Long currencyId;

    @ApiModelProperty(position = 7)
    private Long priceTypeId;

    @ApiModelProperty(position = 7)
    private Long departmentId;

    @ApiModelProperty(position = 7)
    private Long vatSettingId;

    @ApiModelProperty(position = 7)
    private Long vatChartAccountId;

    @ApiModelProperty(position = 7)
    private Long vatSettingRateId;

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

    @ApiModelProperty(position = 17)
    private Integer vatCalculate;

    @ApiModelProperty(position = 12)
    private Double discountAmount;

    @ApiModelProperty(position = 12)
    private Double discountPercent;

    @ApiModelProperty(position = 12)
    private Double totalAmount;

    @ApiModelProperty(position = 12)
    private Double balance;

    @ApiModelProperty(position = 12)
    private Double totalDeposit;

    @ApiModelProperty(position = 21)
    private String note;

    @ApiModelProperty(position = 23)
    private Integer recurrence;

    @ApiModelProperty(position = 23)
    private Integer isPos;

    @ApiModelProperty(position = 23)
    private Integer isApply;

    @ApiModelProperty(position = 23)
    private Integer isApprove;

    @ApiModelProperty(position = 23)
    private Integer isDepositReference;

    @ApiModelProperty(position = 23)
    private Integer isDeposit;

    @ApiModelProperty(position = 23)
    private Integer isNoneVat;

}
