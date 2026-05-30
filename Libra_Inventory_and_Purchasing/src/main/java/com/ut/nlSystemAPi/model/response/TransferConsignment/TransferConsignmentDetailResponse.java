package com.ut.nlSystemAPi.model.response.TransferConsignment;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferConsignmentDetailResponse extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long transferOrderId;

    @ApiModelProperty(position = 2)
    private Long locationFromId;

    @ApiModelProperty(position = 3)
    private String locationFromName;

    @ApiModelProperty(position = 4)
    private Long locationToId;

    @ApiModelProperty(position = 5)
    private String locationToName;

    @ApiModelProperty(position = 5)
    private String expiredDate;

    @ApiModelProperty(position = 6)
    private String poNumber;

    @ApiModelProperty(position = 7)
    private Long productId;

    @ApiModelProperty(position = 7)
    private String productName;

    @ApiModelProperty(position = 7)
    private Long isExpiredDate;

    @ApiModelProperty(position = 7)
    private Long qty;

    @ApiModelProperty(position = 7)
    private Long qtyReceive;

    @ApiModelProperty(position = 7)
    private Long qtyTransfer;

    @ApiModelProperty(position = 7)
    private Long conversion;

    @ApiModelProperty(position = 8)
    private Long uomId;

    @ApiModelProperty(position = 8)
    private String uomName;

    @ApiModelProperty(position = 8)
    private String poNo;

    @ApiModelProperty(position = 8)
    private String sku;

    @ApiModelProperty(position = 8)
    private String upc;

    @ApiModelProperty(position = 8)
    private String lotsNumber;

    @ApiModelProperty(position = 8)
    private Double unitCost;

}
