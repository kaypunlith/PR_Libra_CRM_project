package com.ut.nlSystemAPi.model.response.ProductPriceList;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductPriceListReportResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 1)
    private String sku;

    @ApiModelProperty(position = 2)
    private String upc;

    @ApiModelProperty(position = 3)
    private String productName;

    @ApiModelProperty(position = 4)
    private Long uomId;

    @ApiModelProperty(position = 5)
    private String uom;

    @ApiModelProperty(position = 6)
    private Double unitCost;

    @ApiModelProperty(position = 7)
    private List<ProductPriceListDetailReportResponse> details;

}
