package com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class APScheduleVendorBookedTotalResponse {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(position = 1)
    private Long vendorId;
    @ApiModelProperty(position = 2)
    private String vendorName;
    @ApiModelProperty(position = 3)
    private Double totalBalanceTD;
}
