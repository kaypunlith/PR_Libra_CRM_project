package com.ut.nlSystemAPi.model.response.Quotation;

import com.ut.nlSystemAPi.model.response.Service.ServiceShiftResponse;
import com.ut.nlSystemAPi.model.response.TermCondition.TermConditionResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuotationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Integer type;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 3)
    private String soCode;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private Long companyId;

    @ApiModelProperty(position = 6)
    private String companyName;

    @ApiModelProperty(position = 6)
    private String companyVat;

    @ApiModelProperty(position = 6)
    private String companyTelephone;

    @ApiModelProperty(position = 6)
    private String companyAddress;

    @ApiModelProperty(position = 6)
    private String companyWebsite;

    @ApiModelProperty(position = 6)
    private Long crmQuotationStatusId;

    @ApiModelProperty(position = 6)
    private String crmQuotationStatusName;

    @ApiModelProperty(position = 6)
    private Double crmQuotationStatusPercent;

    @ApiModelProperty(position = 6)
    private String crmQuotationColor;

    @ApiModelProperty(position = 7)
    private Long organizationId;

    @ApiModelProperty(position = 8)
    private String organizationName;

    @ApiModelProperty(position = 9)
    private String organizationCode;

    @ApiModelProperty(position = 10)
    private String organizationAddress;

    @ApiModelProperty(position = 10)
    private String documentAddress;

    @ApiModelProperty(position = 10)
    private String organizationEmail;

    @ApiModelProperty(position = 11)
    private String organizationTelephone;

    @ApiModelProperty(position = 12)
    private Long currencyId;

    @ApiModelProperty(position = 13)
    private String currencyName;

    @ApiModelProperty(position = 14)
    private String currencySymbol;

    @ApiModelProperty(position = 15)
    private Long organizationContactId;

    @ApiModelProperty(position = 16)
    private String organizationContactName;

    @ApiModelProperty(position = 17)
    private String organizationContactTelephone;

    @ApiModelProperty(position = 17)
    private Long priceTypeId;

    @ApiModelProperty(position = 17)
    private String priceTypeName;

    @ApiModelProperty(position = 18)
    private Double subTotal;

    @ApiModelProperty(position = 19)
    private Double totalAmount;

    @ApiModelProperty(position = 20)
    private Long vatId;

    @ApiModelProperty(position = 21)
    private String vatName;

    @ApiModelProperty(position = 22)
    private Double totalVat;

    @ApiModelProperty(position = 23)
    private Double vatPercent;

    @ApiModelProperty(position = 24)
    private Double discountAmount;

    @ApiModelProperty(position = 25)
    private Double discountPercent;

    @ApiModelProperty(position = 26)
    private Double marginPercent;

    @ApiModelProperty(position = 27)
    private Long isClose;

    @ApiModelProperty(position = 28)
    private Long isApprove;

    @ApiModelProperty(position = 29)
    private Long isNoneVat;

    @ApiModelProperty(position = 30)
    private String created;

    @ApiModelProperty(position = 31)
    private String createdBy;

    @ApiModelProperty(position = 32)
    private String modified;

    @ApiModelProperty(position = 33)
    private String modifiedBy;

    @ApiModelProperty(position = 34)
    private Long status;

    @ApiModelProperty(position = 34)
    private Long totalItem;

    @ApiModelProperty(position = 34)
    private Long page;

    @ApiModelProperty(position = 34)
    private Long itemPerPage;

    @ApiModelProperty(position = 35)
    private Long shareSaveOption;

    @ApiModelProperty(position = 36)
    private Long shareOption;

    @ApiModelProperty(position = 37)
    private String shareUserIds;

    @ApiModelProperty(position = 38)
    private String shareExceptUserIds;

    @ApiModelProperty(position = 38)
    private Long isAllowShare;

    @ApiModelProperty(position = 39)
    private String note;

    @ApiModelProperty(position = 42)
    private String approvedBy;

    @ApiModelProperty(position = 43)
    private List<QuotationDetailResponse> shareUser;

    @ApiModelProperty(position = 43)
    private List<QuotationDetailResponse> shareExceptUser;

    @ApiModelProperty(position = 43)
    private List<StatusInformationResponse> statusInformation;

    @ApiModelProperty(position = 43)
    private List<TermConditionResponse> termConditions;

    @ApiModelProperty(position = 43)
    private List<QuotationDetailResponse> details;


    @ApiModelProperty(position = 43)
    private List<ServiceShiftResponse> serviceShiftResponse;

    @ApiModelProperty(position = 44)
    private List<com.ut.nlSystemAPi.model.entity.Quotation.QuotationLog> quotationLogs;
    @ApiModelProperty(position = 45)
    private String createBy;

    @ApiModelProperty(position = 46)
    private String position;

    @ApiModelProperty(position = 47)
    private String tel;

    @ApiModelProperty(position = 48)
    private String email;
}
