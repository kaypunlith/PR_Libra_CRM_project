package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Inventories extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long purchaseReturnId;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 4)
    private Long vendorId;

    @ApiModelProperty(position = 7)
    private Long warehouseId;

    @ApiModelProperty(position = 9)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String date;

    @ApiModelProperty(position = 11)
    private String expireDate;

    @ApiModelProperty(position = 13)
    private String lotsNumber;

    @ApiModelProperty(position = 15)
    private Long qty;

    @ApiModelProperty(position = 16)
    private Long totalOrder;

    @ApiModelProperty(position = 16)
    private Double totalFree;

    @ApiModelProperty(position = 21)
    private Long userId;

    @ApiModelProperty(position = 22)
    private Long customerId;

    @ApiModelProperty(position = 22)
    private Double unitCost;

}
