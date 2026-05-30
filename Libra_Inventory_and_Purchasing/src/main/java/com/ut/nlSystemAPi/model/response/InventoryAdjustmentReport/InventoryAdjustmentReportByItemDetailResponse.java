package com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InventoryAdjustmentReportByItemDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String type;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private String date;

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 6)
    private String locationName;

    @ApiModelProperty(position = 7)
    private String approvedBy;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private Double qtyCalculate;

    @ApiModelProperty(position = 8)
    private Double qty;

    @ApiModelProperty(position = 9)
    private String uomName;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 10)
    private Double totalCost;
}
