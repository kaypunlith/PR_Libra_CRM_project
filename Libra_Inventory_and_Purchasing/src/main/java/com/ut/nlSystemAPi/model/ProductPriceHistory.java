package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPriceHistory extends BaseModel {

    @ApiModelProperty(position = 2)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;

    @ApiModelProperty(position = 2)
    private Long uomId;

    @ApiModelProperty(position = 3)
    private Double amount;

    @ApiModelProperty(position = 4)
    private Double percentage;

    @ApiModelProperty(position = 5)
    private Double addOn;

    @ApiModelProperty(position = 5)
    private Double unitCost;

    @ApiModelProperty(position = 6)
    private Long setType;
}
