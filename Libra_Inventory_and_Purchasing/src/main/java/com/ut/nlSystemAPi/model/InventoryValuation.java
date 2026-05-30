package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventoryValuation extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long purchaseBillId;

    @ApiModelProperty(position = 2)
    private Long purchaseBillDetailId;

    @ApiModelProperty(position = 2)
    private Long purchaseReturnId;

    @ApiModelProperty(position = 2)
    private Long cycleProductId;

    @ApiModelProperty(position = 2)
    private Long cycleProductDetailId;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 2)
    private String reference;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private Long pid;

    @ApiModelProperty(position = 6)
    private Double smallQty;

    @ApiModelProperty(position = 7)
    private Double qty;

    @ApiModelProperty(position = 8)
    private Double cost;

    @ApiModelProperty(position = 9)
    private Double price;

    @ApiModelProperty(position = 10)
    private Integer isVarCost;

}
