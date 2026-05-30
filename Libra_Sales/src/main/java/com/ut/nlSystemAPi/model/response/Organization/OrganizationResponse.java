package com.ut.nlSystemAPi.model.response.Organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String organizationGroupName;

    @ApiModelProperty(position = 3)
    private String organizationCode;

    @ApiModelProperty(position = 4)
    private String organizationName;

    @ApiModelProperty(position = 4)
    private String organizationNameKh;

    @ApiModelProperty(position = 4)
    private String shopName;

    @ApiModelProperty(position = 5)
    private String telephone;

    @ApiModelProperty(position = 6)
    private Long paymentTermId;

    @ApiModelProperty(position = 6)
    private String paymentTermName;

    @ApiModelProperty(position = 6)
    private Long priceTypeId;

    @ApiModelProperty(position = 6)
    private String priceTypeName;

    @ApiModelProperty(position = 7)
    private String contactName;

    @ApiModelProperty(position = 8)
    private String lats;

    @ApiModelProperty(position = 9)
    private String longs;

    @ApiModelProperty(position = 10)
    private String zoneInfo;

    @ApiModelProperty(position = 11)
    private String photo;

    @ApiModelProperty(position = 12)
    private Long businessTypeId;

    @ApiModelProperty(position = 13)
    private String businessTypeName;

    @ApiModelProperty(position = 13)
    private Long businessActivityId;

    @ApiModelProperty(position = 14)
    private String businessActivityName;

    @ApiModelProperty(position = 15)
    private Integer customerType;

    @ApiModelProperty(position = 16)
    private Integer isFreedom;

    @ApiModelProperty(position = 16)
    private Integer isSync;

    @ApiModelProperty(position = 15)
    private Long freedomTypeId;

    @ApiModelProperty(position = 15)
    private String freedomTypeName;

    @ApiModelProperty(position = 17)
    private Long countryId;

    @ApiModelProperty(position = 18)
    private String countryName;

    @ApiModelProperty(position = 19)
    private String houseNo;

    @ApiModelProperty(position = 20)
    private Long streetId;

    @ApiModelProperty(position = 21)
    private String streetName;

    @ApiModelProperty(position = 22)
    private Long provinceId;

    @ApiModelProperty(position = 23)
    private String provinceName;

    @ApiModelProperty(position = 24)
    private Long districtId;

    @ApiModelProperty(position = 25)
    private String districtName;

    @ApiModelProperty(position = 26)
    private Long communeId;

    @ApiModelProperty(position = 27)
    private String communeName;

    @ApiModelProperty(position = 28)
    private Long villageId;

    @ApiModelProperty(position = 29)
    private String villageName;

    @ApiModelProperty(position = 30)
    private String address;

    @ApiModelProperty(position = 31)
    private String mobile;

    @ApiModelProperty(position = 32)
    private String alternateMobile;

    @ApiModelProperty(position = 33)
    private String email;

    @ApiModelProperty(position = 34)
    private String fax;

    @ApiModelProperty(position = 35)
    private String vat;

    @ApiModelProperty(position = 37)
    private String paymentMonthly;

    @ApiModelProperty(position = 38)
    private Double limitCredit;

    @ApiModelProperty(position = 39)
    private Long limitNumberInvoice;

    @ApiModelProperty(position = 40)
    private Long periodFrom;

    @ApiModelProperty(position = 41)
    private Long periodTo;

    @ApiModelProperty(position = 42)
    private Integer recurrence;

    @ApiModelProperty(position = 42)
    private Integer settingInvoiceTax;

    @ApiModelProperty(position = 43)
    private Integer displayOnInvoice;

    @ApiModelProperty(position = 43)
    private String adjCode;

    @ApiModelProperty(position = 43)
    private String productPriceListCode;

    @ApiModelProperty(position = 43)
    private String priceRequestNoCode;

    @ApiModelProperty(position = 43)
    private String requestStockCode;

    @ApiModelProperty(position = 43)
    private String transferCode;

    @ApiModelProperty(position = 43)
    private String transferReceiveCode;

    @ApiModelProperty(position = 43)
    private String purchaseOrderCode;

    @ApiModelProperty(position = 43)
    private String purchaseOrderReceiptCode;

    @ApiModelProperty(position = 43)
    private String saleOrderCode;

    @ApiModelProperty(position = 43)
    private String expenseRequestCode;

    @ApiModelProperty(position = 43)
    private String quotationCode;

    @ApiModelProperty(position = 43)
    private String invoiceCode;

    @ApiModelProperty(position = 43)
    private String posCode;

    @ApiModelProperty(position = 43)
    private String invoiceReceiptCode;

    @ApiModelProperty(position = 43)
    private String dnCode;

    @ApiModelProperty(position = 43)
    private String creditMemoCode;

    @ApiModelProperty(position = 43)
    private String creditReceiptCode;

    @ApiModelProperty(position = 43)
    private String purchaseBillCode;

    @ApiModelProperty(position = 43)
    private String purchaseBillReceiptCode;

    @ApiModelProperty(position = 43)
    private String billReturnCode;

    @ApiModelProperty(position = 43)
    private String billReceiptCode;

    @ApiModelProperty(position = 43)
    private String bomCode;

    @ApiModelProperty(position = 43)
    private String goodReceiptNoteCode;

    @ApiModelProperty(position = 43)
    private String landedCostCode;

    @ApiModelProperty(position = 43)
    private String journalEntryCode;

    @ApiModelProperty(position = 43)
    private String receivePaymentCode;

    @ApiModelProperty(position = 43)
    private String receivePaymentOrgCode;

    @ApiModelProperty(position = 43)
    private String receivePaymentEmpCode;

    @ApiModelProperty(position = 43)
    private String paybillsCode;

    @ApiModelProperty(position = 43)
    private String paybillsJournalCode;

    @ApiModelProperty(position = 43)
    private String created;

    @ApiModelProperty(position = 44)
    private String createdBy;

    @ApiModelProperty(position = 45)
    private String modified;

    @ApiModelProperty(position = 46)
    private String modifiedBy;

    @ApiModelProperty(position = 47)
    private OrganizationActivityCardResponse activityCardRecords;

    @ApiModelProperty(position = 47)
    private List<OrganizationActivityCardResponse> activityCards;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> companies;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> organizationGroups;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> organizationContacts;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> customerContract;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> customerTerminate;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> customerSurvey;

    @ApiModelProperty(position = 47)
    private List<OrganizationDetailResponse> divisionInformation;

    @ApiModelProperty(position = 48)
    private List<OrganizationDetailResponse> freedomCustomers;

}
