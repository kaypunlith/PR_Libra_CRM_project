package com.ut.nlSystemAPi.model.entity.CreditMemo;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMemo extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long companyId;

    @ApiModelProperty(position = 1)
    private Long locationGroupId;

    @ApiModelProperty(position = 1)
    private Long locationId;

    @ApiModelProperty(position = 1)
    private Long customerId;

    @ApiModelProperty(position = 1)
    private Long currencyCenterId;

    @ApiModelProperty(position = 1)
    private Long reasonId;

    @ApiModelProperty(position = 1)
    private Long chartAccountId;

    @ApiModelProperty(position = 1)
    private Long saleOrderId;

    @ApiModelProperty(position = 1)
    private String invoiceCode;

    @ApiModelProperty(position = 1)
    private String invoiceDate;

    @ApiModelProperty(position = 1)
    private String note;

    @ApiModelProperty(position = 1)
    private String cmCode;

    @ApiModelProperty(position = 1)
    private Double totalAmount;

    @ApiModelProperty(position = 1)
    private Double markUp;

    @ApiModelProperty(position = 1)
    private Double discount;

    @ApiModelProperty(position = 1)
    private Double discountPercent;

    @ApiModelProperty(position = 1)
    private String orderDate;

    @ApiModelProperty(position = 1)
    private String dueDate;

    @ApiModelProperty(position = 1)
    private Double totalVat;

    @ApiModelProperty(position = 1)
    private Double vatPercent;

    @ApiModelProperty(position = 1)
    private Long vatSettingId;

    @ApiModelProperty(position = 1)
    private Integer vatCalculate;

    @ApiModelProperty(position = 1)
    private Long vatChartAccountId;

    @ApiModelProperty(position = 1)
    private Long priceTypeId;

    @ApiModelProperty(position = 1)
    private Double balance;

}
