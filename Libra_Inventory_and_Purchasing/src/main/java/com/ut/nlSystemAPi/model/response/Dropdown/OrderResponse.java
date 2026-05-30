package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long customerId;

    @ApiModelProperty(position = 3)
    private Long customerContactId;

    @ApiModelProperty(position = 4)
    private String customerPoNumber;

    @ApiModelProperty(position = 5)
    private Double discount;

    @ApiModelProperty(position = 6)
    private Double discountPercent;

    @ApiModelProperty(position = 7)
    private Long priceTypeId;

    @ApiModelProperty(position = 8)
    private String deliveryDate;

    @ApiModelProperty(position = 9)
    private String orderCode;

    @ApiModelProperty(position = 10)
    private String orderDate;

    @ApiModelProperty(position = 11)
    private String customerName;

    @ApiModelProperty(position = 12)
    private Double netAmount;

    @ApiModelProperty(position = 13)
    private String currencySymbol;

    @ApiModelProperty(position = 13)
    private Integer isWso;

    @ApiModelProperty(position = 14)
    private Long saleRepId;

    @ApiModelProperty(position = 15)
    private String saleRepName;

}
