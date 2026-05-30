package com.ut.nlSystemAPi.model.response.CreditMemo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMemoReceiptResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long creditMemoId;

    @ApiModelProperty(position = 1)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 2)
    private String receiptCode;

    @ApiModelProperty(position = 2)
    private String invoiceCode;

    @ApiModelProperty(position = 3)
    private Long exchangeRateId;

    @ApiModelProperty(position = 3)
    private Double rateToSell;

    @ApiModelProperty(position = 3)
    private String organizationName;

    @ApiModelProperty(position = 4)
    private Long baseCurrencyCenterId;

    @ApiModelProperty(position = 4)
    private String baseCurrencyCenterName;

    @ApiModelProperty(position = 4)
    private String baseCurrencyCenterSymbol;

    @ApiModelProperty(position = 4)
    private Long currencyCenterId;

    @ApiModelProperty(position = 4)
    private String currencyCenterName;

    @ApiModelProperty(position = 4)
    private String currencyCenterSymbol;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 3)
    private String chartAccountName;

    @ApiModelProperty(position = 4)
    private Double paidUsd;

    @ApiModelProperty(position = 5)
    private Double paidOther;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private Double paid;

    @ApiModelProperty(position = 5)
    private Double balance;

    @ApiModelProperty(position = 5)
    private Double balanceOther;

    @ApiModelProperty(position = 6)
    private String date;

    @ApiModelProperty(position = 6)
    private String orderDate;

    @ApiModelProperty(position = 7)
    private String aging;

    @ApiModelProperty(position = 7)
    private String created;

    @ApiModelProperty(position = 8)
    private String createdBy;

    @ApiModelProperty(position = 9)
    private String modified;

    @ApiModelProperty(position = 10)
    private String modifiedBy;
}
