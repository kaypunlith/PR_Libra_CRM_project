package com.ut.nlSystemAPi.model.response.APSchedule.TotalVendorBalance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class APScheduleTopVendorTotalResponseDetails {
    @ApiModelProperty(position = 1)
    private Long purchaseOrderId;
    @ApiModelProperty(position = 2)
    private String orderDate;
    @ApiModelProperty(position = 4)
    private String daysDifference;
    @ApiModelProperty(position = 3)
    private String purchaseOrderCode;

}
