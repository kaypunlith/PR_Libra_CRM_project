package com.ut.nlSystemAPi.model.request.Organization;

import com.ut.nlSystemAPi.model.base.FileBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationRequest {

    @ApiModelProperty(position = 1)
    private Long cloneId;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 2)
    private String organizationCode;

    @ApiModelProperty(position = 3)
    private List<Long> companies;

    @ApiModelProperty(position = 4)
    private Long businessTypeId;

    @ApiModelProperty(position = 5)
    private Long businessActivityId;

    @ApiModelProperty(position = 6)
    private Integer customerType;

    @ApiModelProperty(position = 7)
    private List<Long> organizationGroups;

    @ApiModelProperty(position = 7)
    private Integer isFreedom;

    @ApiModelProperty(position = 7)
    private Integer freedomType;

    @ApiModelProperty(position = 7)
    private List<Long> freedomCustomers;

    @ApiModelProperty(position = 8)
    private String name;

    @ApiModelProperty(position = 9)
    private String nameKh;

    @ApiModelProperty(position = 9)
    private String shopName;

    @ApiModelProperty(position = 10)
    private String lats;

    @ApiModelProperty(position = 11)
    private String longs;

    @ApiModelProperty(position = 12)
    private Long countryId;

    @ApiModelProperty(position = 12)
    private String houseNo;

    @ApiModelProperty(position = 12)
    private Long streetId;

    @ApiModelProperty(position = 12)
    private Long provinceId;

    @ApiModelProperty(position = 12)
    private Long districtId;

    @ApiModelProperty(position = 12)
    private Long communeId;

    @ApiModelProperty(position = 12)
    private Long villageId;

    @ApiModelProperty(position = 12)
    private String address;

    @ApiModelProperty(position = 12)
    private String telephone;

    @ApiModelProperty(position = 13)
    private String mobile;

    @ApiModelProperty(position = 14)
    private String alternateMobile;

    @ApiModelProperty(position = 15)
    private String email;

    @ApiModelProperty(position = 16)
    private String fax;

    @ApiModelProperty(position = 17)
    private List<Long> organizationContacts;

    @ApiModelProperty(position = 18)
    private String vat;

    @ApiModelProperty(position = 19)
    private Long paymentTermId;

    @ApiModelProperty(position = 19)
    private Long priceTypeId;

    @ApiModelProperty(position = 20)
    private String paymentMonthly;

    @ApiModelProperty(position = 21)
    private Double limitCredit;

    @ApiModelProperty(position = 22)
    private Long limitNumberInvoice;

    @ApiModelProperty(position = 23)
    private Long periodFrom;

    @ApiModelProperty(position = 24)
    private Long periodTo;

    @ApiModelProperty(position = 25)
    private Integer recurrence;

    @ApiModelProperty(position = 26)
    private Integer displayOnInvoice;

    @ApiModelProperty(position = 27)
    private Integer settingInvoiceTax;

    @ApiModelProperty(position = 28)
    private List<FileBaseEntity> customerContract;

    @ApiModelProperty(position = 29)
    private List<FileBaseEntity> customerTerminate;

    @ApiModelProperty(position = 30)
    private List<FileBaseEntity> customerSurvey;

    @ApiModelProperty(position = 31)
    private List<OrganizationDivisionRequest> divisionInformation;

    @ApiModelProperty(position = 32)
    private Long branchTypeId;

    @ApiModelProperty(position = 33)
    private String code;

    @ApiModelProperty(position = 34)
    private String abbr;

    @ApiModelProperty(position = 35)
    private String photoUrl;

    @ApiModelProperty(position = 36)
    private String photoName;

    @ApiModelProperty(position = 37)
    private String startWorkHour;

    @ApiModelProperty(position = 38)
    private String endWorkHour;

    @ApiModelProperty(position = 39)
    private String addressKh;

    @ApiModelProperty(position = 40)
    private String adjCode;

    @ApiModelProperty(position = 41)
    private String productPriceListCode;

    @ApiModelProperty(position = 42)
    private String priceRequestNoCode;

    @ApiModelProperty(position = 43)
    private String requestStockCode;

    @ApiModelProperty(position = 44)
    private String transferCode;

    @ApiModelProperty(position = 45)
    private String transferReceiveCode;

    @ApiModelProperty(position = 46)
    private String purchaseOrderCode;

    @ApiModelProperty(position = 47)
    private String purchaseOrderReceiptCode;

    @ApiModelProperty(position = 48)
    private String saleOrderCode;

    @ApiModelProperty(position = 49)
    private String expenseRequestCode;

    @ApiModelProperty(position = 50)
    private String quotationCode;

    @ApiModelProperty(position = 51)
    private String invoiceCode;

    @ApiModelProperty(position = 51)
    private String posCode;

    @ApiModelProperty(position = 52)
    private String invoiceReceiptCode;

    @ApiModelProperty(position = 53)
    private String dnCode;

    @ApiModelProperty(position = 54)
    private String creditMemoCode;

    @ApiModelProperty(position = 55)
    private String creditReceiptCode;

    @ApiModelProperty(position = 56)
    private String purchaseBillCode;

    @ApiModelProperty(position = 57)
    private String purchaseBillReceiptCode;

    @ApiModelProperty(position = 58)
    private String billReturnCode;

    @ApiModelProperty(position = 59)
    private String billReceiptCode;

    @ApiModelProperty(position = 60)
    private String bomCode;

    @ApiModelProperty(position = 61)
    private String goodReceiptNoteCode;

    @ApiModelProperty(position = 62)
    private String landedCostCode;

    @ApiModelProperty(position = 63)
    private String journalEntryCode;

    @ApiModelProperty(position = 64)
    private String receivePaymentCode;

    @ApiModelProperty(position = 65)
    private String receivePaymentOrgCode;

    @ApiModelProperty(position = 66)
    private String receivePaymentEmpCode;

    @ApiModelProperty(position = 67)
    private String paybillsCode;

    @ApiModelProperty(position = 68)
    private String paybillsJournalCode;
}
