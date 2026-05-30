package com.ut.nlSystemAPi.model.response.PriceRequestTracking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceRequestTrackingReportPriceRequestResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private String productCode;

    @ApiModelProperty(position = 2)
    private String productBrand;

    @ApiModelProperty(position = 2)
    private String priceRequestCode;

    @ApiModelProperty(position = 2)
    private String priceRequestDate;

}
