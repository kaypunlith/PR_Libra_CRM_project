package com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class APScheduleTopVendorTotalResponse {
    @ApiModelProperty(position = 1)
    private Long vendorId;
    @ApiModelProperty(position = 2)
    private String vendorName;
    @ApiModelProperty(position = 3)
    private Double totalBalanceTD;
    @ApiModelProperty(position = 4)
    private String vendorPhoto;
    @ApiModelProperty(position = 5)
    private String vendorWorkTelephone;
    @ApiModelProperty(position = 6)
    private String vendorEmail;
    @ApiModelProperty(position = 7)
    private String vendorAddress;
    @ApiModelProperty(position = 8)
    private String vendorCode;
    @ApiModelProperty(position = 9)
    private List<APScheduleTopVendorTotalResponseDetails> vendorDetails;
}
