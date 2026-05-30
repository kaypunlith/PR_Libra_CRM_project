package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignmentDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long transferOrderId;

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

    @ApiModelProperty(position = 9)
    private Long conversion;

}
