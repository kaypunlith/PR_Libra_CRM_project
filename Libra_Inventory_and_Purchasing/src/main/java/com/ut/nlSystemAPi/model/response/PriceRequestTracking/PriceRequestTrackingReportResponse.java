package com.ut.nlSystemAPi.model.response.PriceRequestTracking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceRequestTrackingReportResponse {

    @ApiModelProperty(position = 1)
    private Long createdBy;

    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private String employeeName;

    @ApiModelProperty(position = 3)
    private List<PriceRequestTrackingReportPriceRequestResponse> priceRequest;

    @ApiModelProperty(position = 4)
    private List<PriceRequestTrackingReportQuotationResponse> quotation;

    @ApiModelProperty(position = 5)
    private List<PriceRequestTrackingReportSaleOrderResponse> saleOrder;

}
