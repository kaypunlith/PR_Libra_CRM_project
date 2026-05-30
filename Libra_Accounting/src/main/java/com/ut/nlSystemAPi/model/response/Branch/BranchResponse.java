package com.ut.nlSystemAPi.model.response.Branch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class BranchResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 1)
    private String companyName;

    @ApiModelProperty(position = 1)
    private Long currencyCenterId;

    @ApiModelProperty(position = 3)
    private String currencyCenterSymbol;

    @ApiModelProperty(position = 1)
    private String countryName;

    @ApiModelProperty(position = 2)
    private Long branchTypeId;

    @ApiModelProperty(position = 2)
    private String branchTypeName;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private String nameKh;

    @ApiModelProperty(position = 6)
    private String photoUrl;

    @ApiModelProperty(position = 7)
    private String photoName;

    @ApiModelProperty(position = 6)
    private String abbr;

    @ApiModelProperty(position = 7)
    private String telephone;

    @ApiModelProperty(position = 8)
    private String fax;

    @ApiModelProperty(position = 9)
    private String email;

    @ApiModelProperty(position = 10)
    private String startWorkHour;

    @ApiModelProperty(position = 11)
    private String endWorkHour;

    @ApiModelProperty(position = 12)
    private Long countryId;

    @ApiModelProperty(position = 13)
    private String lats;

    @ApiModelProperty(position = 14)
    private String longs;

    @ApiModelProperty(position = 15)
    private String address;

    @ApiModelProperty(position = 16)
    private String addressKh;

    @ApiModelProperty(position = 17)
    private String adjCode;

    @ApiModelProperty(position = 18)
    private String productPriceListCode;

    @ApiModelProperty(position = 19)
    private String priceRequestNoCode;

    @ApiModelProperty(position = 20)
    private String requestStockCode;

    @ApiModelProperty(position = 21)
    private String transferCode;

    @ApiModelProperty(position = 22)
    private String transferReceiveCode;

    @ApiModelProperty(position = 23)
    private String purchaseOrderCode;

    @ApiModelProperty(position = 24)
    private String purchaseOrderReceiptCode;

    @ApiModelProperty(position = 25)
    private String saleOrderCode;

    @ApiModelProperty(position = 26)
    private String expenseRequestCode;

    @ApiModelProperty(position = 27)
    private String quotationCode;

    @ApiModelProperty(position = 28)
    private String 	invoiceCode;

    @ApiModelProperty(position = 29)
    private String invoiceReceiptCode;

    @ApiModelProperty(position = 30)
    private String dnCode;

    @ApiModelProperty(position = 31)
    private String 	creditMemoCode;

    @ApiModelProperty(position = 32)
    private String creditReceiptCode;

    @ApiModelProperty(position = 33)
    private String 	purchaseBillCode;

    @ApiModelProperty(position = 34)
    private String purchaseBillReceiptCode;

    @ApiModelProperty(position = 35)
    private String billReturnCode;

    @ApiModelProperty(position = 36)
    private String billReceiptCode;

    @ApiModelProperty(position = 37)
    private String bomCode;

    @ApiModelProperty(position = 38)
    private String goodReceiptNoteCode;

    @ApiModelProperty(position = 39)
    private String landedCostCode;

    @ApiModelProperty(position = 40)
    private String  journalEntryCode;

    @ApiModelProperty(position = 41)
    private String receivePaymentCode;

    @ApiModelProperty(position = 43)
    private String 	receivePaymentOrgCode;

    @ApiModelProperty(position = 43)
    private String receivePaymentEmpCode;

    @ApiModelProperty(position = 44)
    private String paybillsCode;

    @ApiModelProperty(position = 45)
    private String 	paybillsJournalCode;

    @ApiModelProperty(position = 44)
    private String created;

    @ApiModelProperty(position = 44)
    private String createdBy;

    @ApiModelProperty(position = 44)
    private String modified;

    @ApiModelProperty(position = 44)
    private String modifiedBy;

    @ApiModelProperty(position = 44)
    private List<BankAccountResponse> bankAccountResponses;

    @ApiModelProperty(position = 44)
    private List<AvailableUserResponse> availableUserResponses;

    @ApiModelProperty(position = 44)
    private List<WarehouseResponse> warehouseResponses;

    @ApiModelProperty(position = 46)
    private List<Long> availableUser;

    @ApiModelProperty(position = 47)
    private List<Long>  availableWareHouse;
}
