package com.ut.nlSystemAPi.model.response.StockAvailableForSale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockAvailableForSaleReportDetailResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 3)
    private String code;

    @ApiModelProperty(position = 4)
    private String upc;

    @ApiModelProperty(position = 5)
    private Long uomId;

    @ApiModelProperty(position = 6)
    private String uom;

    @ApiModelProperty(position = 7)
    private Double totalQtyAvailable;

}
