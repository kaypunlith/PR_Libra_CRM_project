package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PurchaseReceive extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long purchaseReceiveResultId;

    @ApiModelProperty(position = 2)
    private Long purchaseOrderDetailId;

    @ApiModelProperty(position = 3)
    private Long purchaseOrderId;

    @ApiModelProperty(position = 4)
    private Long productId;

    @ApiModelProperty(position = 5)
    private Long qty;

    @ApiModelProperty(position = 6)
    private Long qtyUomId;

    @ApiModelProperty(position = 7)
    private Long conversion;

    @ApiModelProperty(position = 8)
    private String receivedDate;

    @ApiModelProperty(position = 9)
    private String dateExpired;

    @ApiModelProperty(position = 9)
    private String lotNumber;

    @ApiModelProperty(position = 9)
    private String date;

    @ApiModelProperty(position = 3)
    private Long locationId;

    @ApiModelProperty(position = 4)
    private Long locationGroupId;

    @ApiModelProperty(position = 3)
    private Long vendorId;

    @ApiModelProperty(position = 4)
    private Long customerId;

    @ApiModelProperty(position = 4)
    private Double unitCost;

    @ApiModelProperty(position = 4)
    private String code;

}
