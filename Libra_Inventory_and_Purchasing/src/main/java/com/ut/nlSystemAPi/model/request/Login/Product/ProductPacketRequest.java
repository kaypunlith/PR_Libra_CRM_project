package com.ut.nlSystemAPi.model.request.Login.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPacketRequest {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 3)
    private Long qty;

    @ApiModelProperty(position = 4)
    private Long UomId;

    @ApiModelProperty(position = 4)
    private Long conversion;

}
