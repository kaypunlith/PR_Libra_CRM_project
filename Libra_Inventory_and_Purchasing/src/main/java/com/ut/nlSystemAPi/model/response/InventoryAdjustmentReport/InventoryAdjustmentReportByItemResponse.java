package com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InventoryAdjustmentReportByItemResponse {

    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 2)
    private String parentName;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 5)
    private String reference;

    @ApiModelProperty(position = 6)
    private String locationName;

    @ApiModelProperty(position = 7)
    private String createdBy;

    @ApiModelProperty(position = 8)
    private Double unitCost;

    @ApiModelProperty(position = 8)
    private Double qty;

    @ApiModelProperty(position = 8)
    private Double totalCost;

    @ApiModelProperty(position = 8)
    private Double grandTotal;

    @ApiModelProperty(position = 9)
    private List<InventoryAdjustmentReportByItemDetailResponse> details;

}
