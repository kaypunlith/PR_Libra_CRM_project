package com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class APScheduleVendorTotalResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(position = 1)
    private Long vendorId;

    @ApiModelProperty(position = 2)
    private String vendorName;

    @ApiModelProperty(position = 3)
    private Double totalBalanceTD;

}

