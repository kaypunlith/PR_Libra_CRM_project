package com.ut.nlSystemAPi.model.response.SaleInvoice;


import com.ut.nlSystemAPi.model.response.SaleOrder.SaleOrderDetailResponse;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaleInvoiceResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 3)
    private String invoiceDate;

    @ApiModelProperty(position = 3)
    private String invoiceNo;

    @ApiModelProperty(position = 3)
    private Long soId;

    @ApiModelProperty(position = 3)
    private String soNo;

    @ApiModelProperty(position = 3)
    private String poNo;

    @ApiModelProperty(position = 3)
    private String cmCode;

    @ApiModelProperty(position = 4)
    private String soDate;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private String companyEmail;

    @ApiModelProperty(position = 6)
    private String companyName;

    @ApiModelProperty(position = 6)
    private String companyTelephone;

    @ApiModelProperty(position = 6)
    private String companyAddress;

    @ApiModelProperty(position = 6)
    private String companyWebsite;

    @ApiModelProperty(position = 5)
    private Long paymentTermId;

    @ApiModelProperty(position = 6)
    private String paymentTermName;

    @ApiModelProperty(position = 7)
    private Long netDays;

    @ApiModelProperty(position = 8)
    private Long organizationId;

    @ApiModelProperty(position = 9)
    private String organizationName;

    @ApiModelProperty(position = 9)
    private String organizationNameKh;

    @ApiModelProperty(position = 9)
    private String organizationTelephone;

    @ApiModelProperty(position = 10)
    private String organizationCode;

    @ApiModelProperty(position = 11)
    private String organizationAddress;

    @ApiModelProperty(position = 12)
    private String organizationVat;

    @ApiModelProperty(position = 13)
    private Long organizationContactId;

    @ApiModelProperty(position = 14)
    private String organizationContactName;

    @ApiModelProperty(position = 15)
    private String organizationContactTelephone;

    @ApiModelProperty(position = 15)
    private String organizationContactGender;

    @ApiModelProperty(position = 16)
    private Long priceTypeId;

    @ApiModelProperty(position = 17)
    private String priceTypeName;

    @ApiModelProperty(position = 17)
    private Long dateDiff;

    @ApiModelProperty(position = 18)
    private Long currencyId;

    @ApiModelProperty(position = 19)
    private String currencyName;

    @ApiModelProperty(position = 20)
    private String currencySymbol;

    @ApiModelProperty(position = 21)
    private String organizationPoNo;

    @ApiModelProperty(position = 22)
    private Double subTotal;

    @ApiModelProperty(position = 23)
    private Double totalAmount;

    @ApiModelProperty(position = 24)
    private Long vatId;

    @ApiModelProperty(position = 25)
    private String vatName;

    @ApiModelProperty(position = 26)
    private Double vatExchangeRate;

    @ApiModelProperty(position = 26)
    private Double totalVat;

    @ApiModelProperty(position = 27)
    private Double vatPercent;

    @ApiModelProperty(position = 28)
    private Double discountAmount;

    @ApiModelProperty(position = 29)
    private Double discountPercent;

    @ApiModelProperty(position = 30)
    private String deliveryDate;

    @ApiModelProperty(position = 31)
    private String note;

    @ApiModelProperty(position = 32)
    private Long isApply;

    @ApiModelProperty(position = 32)
    private Long organizationRecurrence;

    @ApiModelProperty(position = 32)
    private Long recurrence;

    @ApiModelProperty(position = 32)
    private Long allowModify;

    @ApiModelProperty(position = 32)
    private Long allowDisapprove;

    @ApiModelProperty(position = 33)
    private Long isClose;

    @ApiModelProperty(position = 34)
    private Long isApprove;

    @ApiModelProperty(position = 35)
    private Long isNoneVat;

    @ApiModelProperty(position = 36)
    private Long isRequiredPo;

    @ApiModelProperty(position = 37)
    private String created;

    @ApiModelProperty(position = 38)
    private String createdBy;

    @ApiModelProperty(position = 38)
    private String createdByUser;

    @ApiModelProperty(position = 39)
    private String modified;

    @ApiModelProperty(position = 40)
    private String modifiedBy;

    @ApiModelProperty(position = 41)
    private Long status;

    @ApiModelProperty(position = 42)
    private Double balance;

    @ApiModelProperty(position = 42)
    private String companyVat;

    @ApiModelProperty(position = 42)
    private Double totalCost;

    @ApiModelProperty(position = 42)
    private Double totalDeposit;

    @ApiModelProperty(position = 43)
    private Double totalPrice;

    @ApiModelProperty(position = 44)
    private String approvedBy;

    @ApiModelProperty(position = 44)
    private String soStatus;

    @ApiModelProperty(position = 44)
    private String soStatusColor;

    @ApiModelProperty(position = 44)
    private List<TermConditionResponse> termConditions;

    @ApiModelProperty(position = 45)
    private List<SaleInvoiceDetailResponse> details;

    @ApiModelProperty(position = 46)
    private List<SaleOrderDetailResponse> salesOrderDetails;

    @ApiModelProperty(position = 30)
    private Long departmentId;

    @ApiModelProperty(position = 30)
    private String departmentName;

    @ApiModelProperty(position = 30)
    private Long saleRepId;

    @ApiModelProperty(position = 30)
    private String saleRepName;

    @ApiModelProperty(position = 30)
    private Long deliverId;

    @ApiModelProperty(position = 30)
    private String deliverName;

    @ApiModelProperty(position = 30)
    private Long collectorId;

    @ApiModelProperty(position = 30)
    private String collectorName;

    @ApiModelProperty(position = 30)
    private Long warehouseId;

    @ApiModelProperty(position = 30)
    private String warehouseName;

    @ApiModelProperty(position = 30)
    private Long chartAccountId;

    @ApiModelProperty(position = 30)
    private String chartAccountName;

    @ApiModelProperty(position = 30)
    private Long vatChartAccountId;

    @ApiModelProperty(position = 30)
    private String vatChartAccountName;

    @ApiModelProperty(position = 30)
    private Long quotationId;

    @ApiModelProperty(position = 30)
    private String quotationNumber;

    @ApiModelProperty(position = 30)
    private Long transferOrderId;

    @ApiModelProperty(position = 30)
    private String transferOrderNumber;

    @ApiModelProperty(position = 30)
    private Long bomId;

    @ApiModelProperty(position = 30)
    private String bomCode;

    @ApiModelProperty(position = 30)
    private Integer showName;

    @ApiModelProperty(position = 30)
    private Integer isPos;

    @ApiModelProperty(position = 30)
    private Integer isDeposit;

    @ApiModelProperty(position = 30)
    private Integer isCloseDeposit;

    @ApiModelProperty(position = 30)
    private Double paidAmount;

    @ApiModelProperty(position = 30)
    private Double change;

    @ApiModelProperty(position = 30)
    private Double rateToSell;

    @ApiModelProperty(position = 30)
    private List<InvoiceDepositResponse> invoiceDeposits;
}
