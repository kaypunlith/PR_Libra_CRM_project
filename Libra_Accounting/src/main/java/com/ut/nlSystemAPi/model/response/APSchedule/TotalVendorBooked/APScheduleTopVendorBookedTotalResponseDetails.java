package com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBooked;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class APScheduleTopVendorBookedTotalResponseDetails {
    @ApiModelProperty(position = 1)
    private Long purchaseOrderId;
    @ApiModelProperty(position = 2)
    private String orderDate;
    @ApiModelProperty(position = 3)
    private String purchaseOrderCode;
    @ApiModelProperty(position = 3)
    private Double totalBalanceTD;
    @ApiModelProperty(position = 2)
    private String date;
}
