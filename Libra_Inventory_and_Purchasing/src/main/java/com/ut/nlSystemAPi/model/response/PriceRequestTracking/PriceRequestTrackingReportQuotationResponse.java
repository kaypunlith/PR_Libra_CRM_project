package com.ut.nlSystemAPi.model.response.PriceRequestTracking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceRequestTrackingReportQuotationResponse {

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String productName;

    @ApiModelProperty(position = 2)
    private String productCode;

    @ApiModelProperty(position = 2)
    private String productBrand;

    @ApiModelProperty(position = 2)
    private String quotationCode;

    @ApiModelProperty(position = 2)
    private String quotationDate;

    @ApiModelProperty(position = 7)
    private Long customerId;

    @ApiModelProperty(position = 8)
    private String customerName;

    @ApiModelProperty(position = 9)
    private String customerNameKhmer;

    @ApiModelProperty(position = 10)
    private String unitOfMeasurementId;

    @ApiModelProperty(position = 11)
    private Double unitPrice;

}
