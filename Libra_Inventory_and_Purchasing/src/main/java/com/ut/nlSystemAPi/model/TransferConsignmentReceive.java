package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignmentReceive extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long transferReceiveResultId;

    @ApiModelProperty(position = 2)
    private Long transferOrderId;

    @ApiModelProperty(position = 2)
    private String lotsNumber;

    @ApiModelProperty(position = 3)
    private Long transferOrderDetailId;

    @ApiModelProperty(position = 5)
    private String expiredDate;

    @ApiModelProperty(position = 8)
    private Long productId;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private Long conversion;

}
