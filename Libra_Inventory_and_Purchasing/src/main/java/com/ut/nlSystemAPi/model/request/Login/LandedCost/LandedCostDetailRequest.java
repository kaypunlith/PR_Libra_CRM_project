package com.ut.nlSystemAPi.model.request.Login.LandedCost;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LandedCostDetailRequest extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long type;

    @ApiModelProperty(position = 2, hidden = true)
    private Long landedCostId;

    @ApiModelProperty(position = 3)
    private Long purchaseBillId;

    @ApiModelProperty(position = 3)
    private Long purchaseBillDetailId;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 4)
    private Long itemId;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 10)
    private Double smallValUom;

    @ApiModelProperty(position = 11)
    private Double landedCost;

    @ApiModelProperty(position = 12)
    private Double unitCost;

    @ApiModelProperty(position = 12)
    private Long conversion;

}
