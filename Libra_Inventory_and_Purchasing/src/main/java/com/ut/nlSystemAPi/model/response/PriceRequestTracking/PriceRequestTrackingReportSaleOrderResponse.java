package com.ut.nlSystemAPi.model.response.PriceRequestTracking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceRequestTrackingReportSaleOrderResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 3)
    private String productCode;

    @ApiModelProperty(position = 4)
    private String productBrand;

    @ApiModelProperty(position = 5)
    private Long saleOrderId;

    @ApiModelProperty(position = 6)
    private Long saleOrderStatus;

    @ApiModelProperty(position = 7)
    private String saleOrderCode;

    @ApiModelProperty(position = 8)
    private String saleOrderDate;

    @ApiModelProperty(position = 9)
    private Long organizationId;

    @ApiModelProperty(position = 10)
    private String organizationName;

    @ApiModelProperty(position = 11)
    private String organizationCode;

    @ApiModelProperty(position = 12)
    private Double unitPrice;

    @ApiModelProperty(position = 13)
    private Long uomId;

    @ApiModelProperty(position = 14)
    private String uomName;

}
