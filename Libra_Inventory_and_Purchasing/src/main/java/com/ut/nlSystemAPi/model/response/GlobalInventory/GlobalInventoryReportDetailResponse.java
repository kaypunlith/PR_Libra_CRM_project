package com.ut.nlSystemAPi.model.response.GlobalInventory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GlobalInventoryReportDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String code;

    @ApiModelProperty(position = 3)
    private String upc;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String uom;

    @ApiModelProperty(position = 7)
    private Double lastCost;

    @ApiModelProperty(position = 8)
    private Double endingQty;

    @ApiModelProperty(position = 9)
    private Double qtyOrder;

    @ApiModelProperty(position = 10)
    private Double totalQtyAvailable;

    @ApiModelProperty(position = 11)
    private Long isForSale;
}
