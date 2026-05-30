package com.ut.nlSystemAPi.model.entity.SaleInvoice;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesInvoiceReceipt extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long salesInvoiceId;

    @ApiModelProperty(position = 2)
    private String receiptCode;

    @ApiModelProperty(position = 3)
    private Long exchangeRateId;

    @ApiModelProperty(position = 4)
    private Long currencyCenterId;

    @ApiModelProperty(position = 3)
    private Long chartAccountId;

    @ApiModelProperty(position = 4)
    private Double amountUsd;

    @ApiModelProperty(position = 5)
    private Double amountOther;

    @ApiModelProperty(position = 5)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private Double balance;

    @ApiModelProperty(position = 5)
    private Double balanceOther;

    @ApiModelProperty(position = 5)
    private Double change;

    @ApiModelProperty(position = 5)
    private Double changeOther;

    @ApiModelProperty(position = 6)
    private String date;

    @ApiModelProperty(position = 7)
    private String aging;

}
