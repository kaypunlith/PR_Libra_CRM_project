package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseBillMisc extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long purchaseBillId;

    @ApiModelProperty(position = 2)
    private Long discountId;

    @ApiModelProperty(position = 3)
    private Double discountPercent;

    @ApiModelProperty(position = 4)
    private Double discountAmount;

    @ApiModelProperty(position = 5)
    private String description;

    @ApiModelProperty(position = 6)
    private Double qty;

    @ApiModelProperty(position = 7)
    private Long qtyUomId;

    @ApiModelProperty(position = 8)
    private Long qtyFree;

    @ApiModelProperty(position = 9)
    private Double unitCost;

    @ApiModelProperty(position = 10)
    private Double totalCost;

}
