package com.ut.nlSystemAPi.model.response.StockAvailableForSale;

import com.ut.nlSystemAPi.model.response.InventoryAdjustmentReport.InventoryAdjustmentReportByItemResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StockAvailableForSaleReportResponse {
    @ApiModelProperty(position = 1)
    private Long parentId;

    @ApiModelProperty(position = 2)
    private String parentName;

    @ApiModelProperty(position = 8)
    private Double total;

    @ApiModelProperty(position = 9)
    private Double grandTotal;

    @ApiModelProperty(position = 3)
    private List<StockAvailableForSaleReportDetailResponse> products;
}
