package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GlobalStock extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long purchaseOrderId = null;

    @ApiModelProperty(position = 1)
    private Long purchaseReceiveResultId = null;

    @ApiModelProperty(position = 1)
    private Long cycleProductId = null;

    @ApiModelProperty(position = 1)
    private Long cycleProductDetailId = null;

    @ApiModelProperty(position = 1)
    private Long purchaseReturnId = null;

    @ApiModelProperty(position = 1)
    private Long creditMemoId = null;

    @ApiModelProperty(position = 1)
    private Long salesInvoiceId = null;

    @ApiModelProperty(position = 1)
    private Long vendorId = null;

    @ApiModelProperty(position = 1)
    private Long customerId = null;

    @ApiModelProperty(position = 1)
    private Long transferOrderId = null;

    @ApiModelProperty(position = 2)
    private Long locationId = null;

    @ApiModelProperty(position = 4)
    private Long warehouseId = null;

    @ApiModelProperty(position = 5)
    private String expiredDate = "0000-00-00";

    @ApiModelProperty(position = 6)
    private String date = null;

    @ApiModelProperty(position = 7)
    private Long productId = null;

    @ApiModelProperty(position = 7)
    private Long conversion = 0L;

    @ApiModelProperty(position = 8)
    private Double unitCost = 0D;

    @ApiModelProperty(position = 8)
    private String lotsNumber = "0";

    @ApiModelProperty(position = 8)
    private String type;

    @ApiModelProperty(position = 7)
    private Long totalOrder = 0L;

    @ApiModelProperty(position = 7)
    private Long totalFree = 0L;

    @ApiModelProperty(position = 7)
    private Long totalQty = 0L;

    @ApiModelProperty(position = 7)
    private Long totalCycle = 0L;

    @ApiModelProperty(position = 7)
    private Long totalSo = 0L;

    @ApiModelProperty(position = 7)
    private Long totalSoFree = 0L;

    @ApiModelProperty(position = 7)
    private Long totalPos = 0L;

    @ApiModelProperty(position = 7)
    private Long totalPosFree = 0L;

    @ApiModelProperty(position = 7)
    private Long totalPb = 0L;

    @ApiModelProperty(position = 7)
    private Long totalPbc = 0L;

    @ApiModelProperty(position = 7)
    private Long totalCm = 0L;

    @ApiModelProperty(position = 7)
    private Long totalCmFree = 0L;

    @ApiModelProperty(position = 7)
    private Long totalToIn = 0L;

    @ApiModelProperty(position = 7)
    private Long totalToOut = 0L;

    @ApiModelProperty(position = 7)
    private Long totalUpdate = 0L;
}
