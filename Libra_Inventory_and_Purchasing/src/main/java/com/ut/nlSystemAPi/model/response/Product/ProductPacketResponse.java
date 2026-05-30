package com.ut.nlSystemAPi.model.response.Product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPacketResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private Long packetId;

    @ApiModelProperty(position = 3)
    private Long qty;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 4)
    private String uomName;

    @ApiModelProperty(position = 5)
    private Long conversion;
}
