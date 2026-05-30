package com.ut.nlSystemAPi.model.entity.SaleOrder;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SaleOrderMisc extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long saleOrderId;

    @ApiModelProperty(position = 2)
    private Long quotationId;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 2)
    private String poNo;

    @ApiModelProperty(position = 4)
    private Long qty;

    @ApiModelProperty(position = 4)
    private Long qtyFree;

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
