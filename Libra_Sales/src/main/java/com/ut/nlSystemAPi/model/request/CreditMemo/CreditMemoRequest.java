package com.ut.nlSystemAPi.model.request.CreditMemo;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreditMemoRequest extends BaseModel {

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String moduleCode;

    @ApiModelProperty(position = 4)
    private Long locationGroupId;

    @ApiModelProperty(position = 5)
    private Long locationId;

    @ApiModelProperty(position = 6)
    private Long customerId;

    @ApiModelProperty(position = 8)
    private Long cmTermId;

    @ApiModelProperty(position = 9)
    private Long saleInvoiceId;

    @ApiModelProperty(position = 10)
    private String invoiceCode;

    @ApiModelProperty(position = 11)
    private String invoiceDate;

    @ApiModelProperty(position = 12)
    private String note;

    @ApiModelProperty(position = 13)
    private String cmCode;

    @ApiModelProperty(position = 14)
    private Double subTotal;

    @ApiModelProperty(position = 15)
    private Double markUp;

    @ApiModelProperty(position = 16)
    private Double discount;

    @ApiModelProperty(position = 17)
    private Double discountPercent;

    @ApiModelProperty(position = 18)
    private String orderDate;

    @ApiModelProperty(position = 19)
    private String dueDate;

    @ApiModelProperty(position = 20)
    private Double totalVat;

    @ApiModelProperty(position = 21)
    private Double vatPercent;

    @ApiModelProperty(position = 22)
    private Long vatSettingId;

    @ApiModelProperty(position = 23)
    private Integer vatCalculate;

    @ApiModelProperty(position = 24)
    private Long priceTypeId;

    @ApiModelProperty(position = 25)
    private Long chartAccountId;

    @ApiModelProperty(position = 28)
    private List<CreditMemoRequestDetail> details;

}
