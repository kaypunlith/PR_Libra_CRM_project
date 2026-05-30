package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseBillDropdownResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 3)
    private String poCode;

    @ApiModelProperty(position = 3)
    private String orderDate;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 5)
    private String vendorName;

    @ApiModelProperty(position = 6)
    private Long locationId;

    @ApiModelProperty(position = 7)
    private String locationName;

    @ApiModelProperty(position = 8)
    private Double balance;

    @ApiModelProperty(position = 9)
    private Double totalAmount;

    @ApiModelProperty(position = 10)
    private Long exchangeRateId;

    @ApiModelProperty(position = 10)
    private Double rateToPurchase;

    @ApiModelProperty(position = 10)
    private Long currencyCenterId;

    @ApiModelProperty(position = 11)
    private String currencySymbol;

    @ApiModelProperty(position = 12)
    private Long status;

}
