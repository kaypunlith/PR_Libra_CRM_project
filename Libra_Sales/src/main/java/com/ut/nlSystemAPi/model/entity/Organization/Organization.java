package com.ut.nlSystemAPi.model.entity.Organization;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Organization extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String photo;

    @ApiModelProperty(position = 2)
    private Long sourceId;

    @ApiModelProperty(position = 2)
    private String organizationCode;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private Long businessTypeId;

    @ApiModelProperty(position = 2)
    private Long businessActivityId;

    @ApiModelProperty(position = 2)
    private Integer customerType;

    @ApiModelProperty(position = 2)
    private Integer isFreedom;

    @ApiModelProperty(position = 2)
    private Integer freedomType;

    @ApiModelProperty(position = 2)
    private Long organizationGroupId;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 2)
    private String nameKh;

    @ApiModelProperty(position = 2)
    private String shopName;

    @ApiModelProperty(position = 2)
    private String lats;

    @ApiModelProperty(position = 2)
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

    @ApiModelProperty(position = 2)
    private String telephone;

    @ApiModelProperty(position = 2)
    private String mobile;

    @ApiModelProperty(position = 2)
    private String alternateMobile;

    @ApiModelProperty(position = 2)
    private String email;

    @ApiModelProperty(position = 2)
    private String fax;

    @ApiModelProperty(position = 2)
    private Long organizationContactId;

    @ApiModelProperty(position = 2)
    private String vat;

    @ApiModelProperty(position = 2)
    private Long paymentTermId;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;

    @ApiModelProperty(position = 2)
    private String paymentMonthly;

    @ApiModelProperty(position = 2)
    private Double limitCredit;

    @ApiModelProperty(position = 2)
    private Long limitNumberInvoice;

    @ApiModelProperty(position = 2)
    private Long periodFrom;

    @ApiModelProperty(position = 2)
    private Long periodTo;

    @ApiModelProperty(position = 2)
    private Integer recurrence;

    @ApiModelProperty(position = 2)
    private Integer displayOnInvoice;

    @ApiModelProperty(position = 2)
    private Integer settingInvoiceTax;

    @ApiModelProperty(position = 2)
    private String customerContract;

    @ApiModelProperty(position = 2)
    private String customerTerminate;

    @ApiModelProperty(position = 2)
    private String customerSurvey;

    @ApiModelProperty(position = 2)
    private String customerContractUrl;

    @ApiModelProperty(position = 2)
    private String customerTerminateUrl;

    @ApiModelProperty(position = 2)
    private String customerSurveyUrl;

    @ApiModelProperty(position = 2)
    private Long branchTypeId;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 2)
    private String abbr;

    @ApiModelProperty(position = 2)
    private String photoUrl;

    @ApiModelProperty(position = 2)
    private String photoName;

    @ApiModelProperty(position = 2)
    private String startWorkHour;

    @ApiModelProperty(position = 2)
    private String endWorkHour;

    @ApiModelProperty(position = 2)
    private String addressKh;

    @ApiModelProperty(position = 2)
    private String adjCode;

    @ApiModelProperty(position = 2)
    private String productPriceListCode;

    @ApiModelProperty(position = 2)
    private String priceRequestNoCode;

    @ApiModelProperty(position = 2)
    private String requestStockCode;

    @ApiModelProperty(position = 2)
    private String transferCode;

    @ApiModelProperty(position = 2)
    private String transferReceiveCode;

    @ApiModelProperty(position = 2)
    private String purchaseOrderCode;

    @ApiModelProperty(position = 2)
    private String purchaseOrderReceiptCode;

    @ApiModelProperty(position = 2)
    private String saleOrderCode;

    @ApiModelProperty(position = 2)
    private String expenseRequestCode;

    @ApiModelProperty(position = 2)
    private String quotationCode;

    @ApiModelProperty(position = 2)
    private String invoiceCode;

    @ApiModelProperty(position = 2)
    private String posCode;

    @ApiModelProperty(position = 2)
    private String invoiceReceiptCode;

    @ApiModelProperty(position = 2)
    private String dnCode;

    @ApiModelProperty(position = 2)
    private String creditMemoCode;

    @ApiModelProperty(position = 2)
    private String creditReceiptCode;

    @ApiModelProperty(position = 2)
    private String purchaseBillCode;

    @ApiModelProperty(position = 2)
    private String purchaseBillReceiptCode;

    @ApiModelProperty(position = 2)
    private String billReturnCode;

    @ApiModelProperty(position = 2)
    private String billReceiptCode;

    @ApiModelProperty(position = 2)
    private String bomCode;

    @ApiModelProperty(position = 2)
    private String goodReceiptNoteCode;

    @ApiModelProperty(position = 2)
    private String landedCostCode;

    @ApiModelProperty(position = 2)
    private String journalEntryCode;

    @ApiModelProperty(position = 2)
    private String receivePaymentCode;

    @ApiModelProperty(position = 2)
    private String receivePaymentOrgCode;

    @ApiModelProperty(position = 2)
    private String receivePaymentEmpCode;

    @ApiModelProperty(position = 2)
    private String payBillCode;

    @ApiModelProperty(position = 2)
    private String payJournalCode;

}
