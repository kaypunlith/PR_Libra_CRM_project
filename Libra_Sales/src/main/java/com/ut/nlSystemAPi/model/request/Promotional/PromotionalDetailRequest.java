package com.ut.nlSystemAPi.model.request.Promotional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PromotionalDetailRequest {

    @ApiModelProperty(position = 1)
    private Long uomId;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Double qtyFree;

    @ApiModelProperty(position = 3)
    private Double discountQty;

    @ApiModelProperty(position = 4)
    private Double discountPercent;

    @ApiModelProperty(position = 5)
    private Double discountAmount;

    @ApiModelProperty(position = 6)
    private Double specialPrice;

    @ApiModelProperty(position = 7)
    private List<PromotionalSubDetailRequest> subDetails;
}
