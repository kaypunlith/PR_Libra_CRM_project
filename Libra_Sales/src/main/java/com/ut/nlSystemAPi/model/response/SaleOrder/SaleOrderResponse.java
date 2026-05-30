package com.ut.nlSystemAPi.model.response.SaleOrder;

import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaleOrderResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 3)
    private String soNo;

    @ApiModelProperty(position = 3)
    private String poNo;

    @ApiModelProperty(position = 3)
    private String invoiceNo;

    @ApiModelProperty(position = 4)
    private String soDate;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private String companyName;

    @ApiModelProperty(position = 7)
    private String companyVat;

    @ApiModelProperty(position = 7)
    private String companyTelephone;

    @ApiModelProperty(position = 8)
    private String companyAddress;

    @ApiModelProperty(position = 9)
    private String companyWebsite;

    @ApiModelProperty(position = 8)
    private Long organizationId;

    @ApiModelProperty(position = 9)
    private String organizationName;

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

    @ApiModelProperty(position = 16)
    private Long priceTypeId;

    @ApiModelProperty(position = 17)
    private String priceTypeName;

    @ApiModelProperty(position = 17)
    private Integer dateDiff;

    @ApiModelProperty(position = 18)
    private Long currencyId;

    @ApiModelProperty(position = 19)
    private String currencyName;

    @ApiModelProperty(position = 20)
    private String currencySymbol;

    @ApiModelProperty(position = 21)
    private String organizationPoNo;

    @ApiModelProperty(position = 21)
    private String organizationPoFile;

    @ApiModelProperty(position = 22)
    private Double subTotal;

    @ApiModelProperty(position = 23)
    private Double totalAmount;

    @ApiModelProperty(position = 24)
    private Long vatId;

    @ApiModelProperty(position = 25)
    private String vatName;

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
    private Integer allowModify;

    @ApiModelProperty(position = 32)
    private Integer allowDisapprove;

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

    @ApiModelProperty(position = 39)
    private String modified;

    @ApiModelProperty(position = 40)
    private String modifiedBy;

    @ApiModelProperty(position = 41)
    private Long editCount;

    @ApiModelProperty(position = 41)
    private Long status;

    @ApiModelProperty(position = 42)
    private Double totalCost;

    @ApiModelProperty(position = 43)
    private Double totalPrice;

    @ApiModelProperty(position = 44)
    private String approvedBy;

    @ApiModelProperty(position = 44)
    private String soStatus;

    @ApiModelProperty(position = 44)
    private Integer soStatusColor;

    @ApiModelProperty(position = 44)
    private Integer isWso;

    @ApiModelProperty(position = 45)
    private List<SaleOrderQuotationResponse> quotations;

    @ApiModelProperty(position = 45)
    private List<TermConditionResponse> termConditions;

    @ApiModelProperty(position = 46)
    private List<SaleOrderDetailResponse> details;
}
