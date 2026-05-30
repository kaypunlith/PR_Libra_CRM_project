package com.ut.nlSystemAPi.model.response.PriceRequest;

import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportQuotationResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportSaleOrderResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceRequestResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private String requestDate;

    @ApiModelProperty(position = 3)
    private String priceRequest;

    @ApiModelProperty(position = 4)
    private String requestBy;

    @ApiModelProperty(position = 5)
    private String organizationName;

    @ApiModelProperty(position = 6)
    private String brand;

    @ApiModelProperty(position = 7)
    private String model;

    @ApiModelProperty(position = 8)
    private Long qty;

    @ApiModelProperty(position = 8)
    private Long isConvert;

    @ApiModelProperty(position = 8)
    private String percentageName;

    @ApiModelProperty(position = 8)
    private String priorityColor;

    @ApiModelProperty(position = 8)
    private String priorityName;

    @ApiModelProperty(position = 9)
    private Long prStatusId;

    @ApiModelProperty(position = 42)
    private String filePdf;

    @ApiModelProperty(position = 42)
    private String filePdfName;

    @ApiModelProperty(position = 43)
    private String filePhoto;

    @ApiModelProperty(position = 43)
    private String filePhotoName;

    @ApiModelProperty(position = 9)
    private String prStatusName;

    @ApiModelProperty(position = 10)
    private String leadTimeName;

    @ApiModelProperty(position = 11)
    private Long status;

    @ApiModelProperty(position = 12)
    private Long ctp;

    @ApiModelProperty(position = 13)
    private String note;

    @ApiModelProperty(position = 14)
    private String createdBy;

    @ApiModelProperty(position = 16)
    private String deliveryStatus;

    @ApiModelProperty(position = 17)
    private Long telegramPushAmount;

    @ApiModelProperty(position = 18)
    private List<PriceRequestTrackingReportQuotationResponse> quotations;

    @ApiModelProperty(position = 18)
    private List<PriceRequestTrackingReportSaleOrderResponse> saleOrders;

}
