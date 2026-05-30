package com.ut.nlSystemAPi.model.entity.Promotional;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionalDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long promotionalId;

    @ApiModelProperty(position = 3)
    private Long uomId;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Double qtyFree;

    @ApiModelProperty(position = 5)
    private Double discountQty;

    @ApiModelProperty(position = 6)
    private Double discountPercent;

    @ApiModelProperty(position = 7)
    private Double discountAmount;

    @ApiModelProperty(position = 8)
    private Double price;
}
