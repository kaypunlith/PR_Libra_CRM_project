package com.ut.nlSystemAPi.model.response.PriceRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportQuotationResponse;
import com.ut.nlSystemAPi.model.response.PriceRequestTracking.PriceRequestTrackingReportSaleOrderResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.json.JSONPropertyIgnore;

import java.util.List;

@Data
public class PriceRequestResponseDetail {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 1)
    private Long productId;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 5)
    private Long organizationId;

    @ApiModelProperty(position = 6)
    private String organizationName;

    @ApiModelProperty(position = 7)
    private String brand;

    @ApiModelProperty(position = 8)
    private String requestDate;

    @ApiModelProperty(position = 8)
    private Long isConvert;

    @ApiModelProperty(position = 10)
    private Long requestBy;

    @ApiModelProperty(position = 11)
    private String requestByName;

    @ApiModelProperty(position = 12)
    private String customerContactName;

    @ApiModelProperty(position = 13)
    private Long qty;

    @ApiModelProperty(position = 14)
    private String requestCode;

    @ApiModelProperty(position = 15)
    private String dateExpected;

    @ApiModelProperty(position = 16)
    private String modelName;

    @ApiModelProperty(position = 17)
    private Long percentageId;

    @ApiModelProperty(position = 18)
    private String percentageName;

    @ApiModelProperty(position = 19)
    private Long priorityId;

    @ApiModelProperty(position = 20)
    private String priorityName;

    @ApiModelProperty(position = 21)
    private String misc;

    @ApiModelProperty(position = 22)
    private String spec;

    @ApiModelProperty(position = 23)
    private String note;

    @ApiModelProperty(position = 24)
    private String price;

    @ApiModelProperty(position = 25)
    private String termOfPayment;

    @ApiModelProperty(position = 26)
    private String url;

    @ApiModelProperty(position = 27)
    private Long shipFromId;

    @ApiModelProperty(position = 28)
    private String shipFromName;

    @ApiModelProperty(position = 29)
    private Long currencyCenterId;

    @ApiModelProperty(position = 30)
    private String currencyCenterName;

    @ApiModelProperty(position = 31)
    private String vendorName;

    @ApiModelProperty(position = 32)
    private Long madeInCountryId;

    @ApiModelProperty(position = 33)
    private String madeInCountryName;

    @ApiModelProperty(position = 35)
    private Long prStatusId;

    @ApiModelProperty(position = 35)
    private String prStatusName;

    @ApiModelProperty(position = 35)
    private Long closeReasonId;

    @ApiModelProperty(position = 35)
    private String closeReasonName;

    @ApiModelProperty(position = 35)
    private String closedDate;

    @ApiModelProperty(position = 35)
    private Long closedBy;

    @ApiModelProperty(position = 35)
    private String closedByName;

    @ApiModelProperty(position = 35)
    private String approvedBy;

    @ApiModelProperty(position = 35)
    private String rejectedBy;

    @ApiModelProperty(position = 36)
    private Long uomId;

    @ApiModelProperty(position = 37)
    private String uomsName;

    @ApiModelProperty(position = 38)
    private Long leadTimeId;

    @ApiModelProperty(position = 39)
    private String leadTimeName;

    @ApiModelProperty(position = 42)
    private String filePdf;

    @ApiModelProperty(position = 42)
    private String filePdfName;

    @ApiModelProperty(position = 43)
    private String filePhoto;

    @ApiModelProperty(position = 43)
    private String filePhotoName;

    @ApiModelProperty(position = 44)
    private String photo1;

    @ApiModelProperty(position = 44)
    private String photo1Name;

    @ApiModelProperty(position = 45)
    private String photo2;

    @ApiModelProperty(position = 45)
    private String photo2Name;

    @ApiModelProperty(position = 46)
    private String photo3;

    @ApiModelProperty(position = 46)
    private String photo3Name;

    @ApiModelProperty(position = 47)
    private String photo4;

    @ApiModelProperty(position = 47)
    private String photo4Name;

    @ApiModelProperty(position = 18)
    private List<PriceRequestTrackingReportQuotationResponse> quotations;

    @ApiModelProperty(position = 18)
    private List<PriceRequestTrackingReportSaleOrderResponse> saleOrders;

}
