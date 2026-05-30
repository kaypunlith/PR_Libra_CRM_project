package com.ut.nlSystemAPi.model.response.InventoryAdjustment;

import com.ut.nlSystemAPi.model.InventoryAdjustmentDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class InventoryAdjustmentDetailsResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long inventoryAdjustmentId;

    @ApiModelProperty(position = 3)
    private Long productId;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 3)
    private String sku;

    @ApiModelProperty(position = 3)
    private String upc;

    @ApiModelProperty(position = 4)
    private Double unitCost;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 4)
    private String uomName;

    @ApiModelProperty(position = 4)
    private String uomAbbr;

    @ApiModelProperty(position = 5)
    private Long isExpiredDate;

    @ApiModelProperty(position = 5)
    private String expiredDate;

    @ApiModelProperty(position = 5)
    private String lotsNumber;

    @ApiModelProperty(position = 6)
    private Long locationId;

    @ApiModelProperty(position = 6)
    private String locationName;

    @ApiModelProperty(position = 6)
    private Long newQty;

    @ApiModelProperty(position = 7)
    private Long adjustQty;

    @ApiModelProperty(position = 8)
    private Long qtyOnhand;
}
