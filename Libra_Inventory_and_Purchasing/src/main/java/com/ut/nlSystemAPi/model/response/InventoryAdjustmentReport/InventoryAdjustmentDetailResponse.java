package com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InventoryAdjustmentDetailResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 3)
    private List<InventoryAdjustmentReportByItemResponse> inventoryAdjustmentReportByItemResponses;

}
