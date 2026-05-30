package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LandedCostPurchaseBill extends BaseModel {

    @ApiModelProperty(position = 14)
    private Long landedCostId;

    @ApiModelProperty(position = 17)
    private Long purchaseBillId;

}
