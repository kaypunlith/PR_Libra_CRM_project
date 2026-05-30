package com.ut.nlSystemAPi.model.response.GoodReceiptNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListOrderDetailResponse {
    @ApiModelProperty(position = 1)
    private String code;

    @ApiModelProperty(position = 2)
    private String productId;

    @ApiModelProperty(position = 3)
    private String qty;

    @ApiModelProperty(position = 4)
    private String qtyUomId;

    @ApiModelProperty(position = 5)
    private String conversion;

}
