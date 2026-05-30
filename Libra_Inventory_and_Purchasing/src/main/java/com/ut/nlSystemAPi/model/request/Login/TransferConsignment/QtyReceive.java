package com.ut.nlSystemAPi.model.request.Login.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QtyReceive {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long qtyReceive;

}
