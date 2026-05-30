package com.ut.nlSystemAPi.model.response.PriceRequest;

import com.ut.nlSystemAPi.model.request.Login.VendorManagement.VendorPhoto;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportQuotationResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportSaleOrderResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceRequestApproveResponse {

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

    @ApiModelProperty(position = 10)
    private String leadTimeName;

    @ApiModelProperty(position = 11)
    private Long status;

    @ApiModelProperty(position = 13)
    private String uomName;

    @ApiModelProperty(position = 13)
    private String vendorName;

    @ApiModelProperty(position = 13)
    private String convertBy;

    @ApiModelProperty(position = 13)
    private Double unitCost;

    @ApiModelProperty(position = 18)
    private String refDoc;

    @ApiModelProperty(position = 19)
    private String refDocName;

    @ApiModelProperty(position = 20)
    private String photo;

    @ApiModelProperty(position = 21)
    private String photoName;

}
