package com.ut.nlSystemAPi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductPacket {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long packetId;

    @ApiModelProperty(position = 3)
    private Long qty;

    @ApiModelProperty(position = 4)
    private Long qtyUomId;

    @ApiModelProperty(position = 5)
    private Long conversion;
}
