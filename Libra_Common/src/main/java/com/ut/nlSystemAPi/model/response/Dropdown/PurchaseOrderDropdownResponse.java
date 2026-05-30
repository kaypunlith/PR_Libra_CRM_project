package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseOrderDropdownResponse {

    @ApiModelProperty(position = 1, hidden = true)
    private Long paymentTermId;

    @ApiModelProperty(position = 2, hidden = true)
    private Long pvRequestId;

    @ApiModelProperty(position = 3)
    private Long id;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private Double balance;

    @ApiModelProperty(position = 6)
    private String code;

    @ApiModelProperty(position = 7)
    private Long applyToId;

    @ApiModelProperty(position = 8)
    private Long applyToType;

    @ApiModelProperty(position = 9)
    private String applyToName;

    @ApiModelProperty(position = 10)
    private Long hasGoodReceiptNote;

    @ApiModelProperty(position = 11)
    private Long paymentType;

    @ApiModelProperty(position = 12)
    private String poCode;

    @ApiModelProperty(position = 13)
    private String orderDate;

    @ApiModelProperty(position = 14)
    private Long vendorId;

    @ApiModelProperty(position = 15)
    private String vendorName;

    @ApiModelProperty(position = 16)
    private Long warehouseId;

    @ApiModelProperty(position = 17)
    private String warehouseName;

    @ApiModelProperty(position = 18)
    private Double totalAmount;

    @ApiModelProperty(position = 19)
    private Long exchangeRateId;

    @ApiModelProperty(position = 20)
    private Double rateToPurchase;

    @ApiModelProperty(position = 21)
    private Long currencyCenterId;

    @ApiModelProperty(position = 22)
    private String currencyName;

    @ApiModelProperty(position = 23)
    private String currencySymbol;

    @ApiModelProperty(position = 24)
    private Long erApprove;

    @ApiModelProperty(position = 25)
    private java.util.List<PurchaseOrderDetailDropdownResponse> details;
}
