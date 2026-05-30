package com.ut.nlSystemAPi.model.request.SaleOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaleOrderDetailRequest {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 2)
    private String poNo;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 2)
    private Long quotationId;

    @ApiModelProperty(position = 4)
    private Long foc;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 7)
    private Long discountId;

    @ApiModelProperty(position = 7)
    private Double discountAmount;

    @ApiModelProperty(position = 7)
    private Double discountPercent;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Double totalPrice;

}
