package com.ut.nlSystemAPi.model.response.Report.Sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SalesOrderReportDetailResponse {

    @ApiModelProperty(position = 1)
    private Integer type;

    @ApiModelProperty(position = 2)
    private String itemName;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 9)
    private String uomName;

    @ApiModelProperty(position = 9)
    private String sku;

    @ApiModelProperty(position = 9)
    private String upc;

    @ApiModelProperty(position = 10)
    private Double unitCost;

    @ApiModelProperty(position = 11)
    private Double totalCost;

}