package com.ut.nlSystemAPi.model.request.Promotional;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PromotionalSubDetailRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long uomId;

    @ApiModelProperty(position = 3)
    private Double qty;
}
