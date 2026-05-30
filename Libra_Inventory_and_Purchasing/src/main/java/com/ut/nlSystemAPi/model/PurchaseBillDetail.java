package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseBillDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 1)
    private Long purchaseBillId;

    @ApiModelProperty(position = 2)
    private Long itemId;

    @ApiModelProperty(position = 3)
    private String note;

    @ApiModelProperty(position = 3)
    private String expiredDate;

    @ApiModelProperty(position = 4)
    private Double qty;

    @ApiModelProperty(position = 4)
    private Double foc;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 5)
    private Long smallValUom;

    @ApiModelProperty(position = 6)
    private Double totalCost;

    @ApiModelProperty(position = 6)
    private Long discountId;

    @ApiModelProperty(position = 7)
    private Double discountAmount;

    @ApiModelProperty(position = 7)
    private Double discountPercent;

    @ApiModelProperty(position = 7)
    private Double unitCost;

    @ApiModelProperty(position = 7)
    private Double defaultCost;

    @ApiModelProperty(position = 7)
    private Double newCost;

}
