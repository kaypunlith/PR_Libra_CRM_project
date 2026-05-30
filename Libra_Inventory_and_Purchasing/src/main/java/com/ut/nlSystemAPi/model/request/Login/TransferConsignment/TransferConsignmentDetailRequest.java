package com.ut.nlSystemAPi.model.request.Login.TransferConsignment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignmentDetailRequest {

    @ApiModelProperty(position = 3)
    private Long fromLocationId;

    @ApiModelProperty(position = 4)
    private Long toLocationId;

    @ApiModelProperty(position = 5)
    private String expiredDate;

    @ApiModelProperty(position = 6)
    private String poNumber;

    @ApiModelProperty(position = 7)
    private Long productId;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private Long conversion;

}
