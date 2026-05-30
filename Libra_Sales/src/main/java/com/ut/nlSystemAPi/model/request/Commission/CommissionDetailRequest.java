package com.ut.nlSystemAPi.model.request.Commission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionDetailRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Double qty;

    @ApiModelProperty(position = 3)
    private Long uomId;

    @ApiModelProperty(position = 4)
    private Double amount;

    @ApiModelProperty(position = 5)
    private Double percent;

    @ApiModelProperty(position = 6)
    private Double unitPrice;

}

