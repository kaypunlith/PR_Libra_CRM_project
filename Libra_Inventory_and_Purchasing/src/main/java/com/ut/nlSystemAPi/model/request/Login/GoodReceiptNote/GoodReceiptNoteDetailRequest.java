package com.ut.nlSystemAPi.model.request.Login.GoodReceiptNote;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodReceiptNoteDetailRequest {

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private String expireDate;

    @ApiModelProperty(position = 5)
    private Long conversion;

    @ApiModelProperty(position = 5)
    private Long qty;

    @ApiModelProperty(position = 5)
    private Long qtyReceive;

    @ApiModelProperty(position = 6)
    private Long uomId;
}
