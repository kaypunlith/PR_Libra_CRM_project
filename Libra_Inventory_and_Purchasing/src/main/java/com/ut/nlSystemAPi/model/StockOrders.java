package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockOrders extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long transferOrderId;

    @ApiModelProperty(position = 3)
    private Long locationFromId;

    @ApiModelProperty(position = 3)
    private Long locationToId;

    @ApiModelProperty(position = 4)
    private Long warehouseFromId;

    @ApiModelProperty(position = 4)
    private Long warehouseToId;

    @ApiModelProperty(position = 5)
    private Long productId;

    @ApiModelProperty(position = 5)
    private Long lotsNumber;

    @ApiModelProperty(position = 6)
    private Long qty;

    @ApiModelProperty(position = 6)
    private String symbol;

    @ApiModelProperty(position = 6)
    private Long totalOrder;

    @ApiModelProperty(position = 6)
    private String expireDate;

    @ApiModelProperty(position = 7)
    private Long qtyUomId;

    @ApiModelProperty(position = 8)
    private Long conversion;


}
