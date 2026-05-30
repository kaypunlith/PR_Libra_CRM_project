package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodReceiptNoteDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long purchaseReceiveResultId;

    @ApiModelProperty(position = 2)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 3)
    private Long purchaseOrderDetailId;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 5)
    private String expireDate;

    @ApiModelProperty(position = 6)
    private Long qty;

    @ApiModelProperty(position = 6)
    private Long conversion;

    @ApiModelProperty(position = 7)
    private Long qtyUomId;


}
