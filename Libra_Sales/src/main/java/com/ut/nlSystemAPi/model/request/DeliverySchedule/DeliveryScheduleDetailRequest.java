package com.ut.nlSystemAPi.model.request.DeliverySchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryScheduleDetailRequest {

    @ApiModelProperty(position = 2)
    private Long saleOrderId;

    @ApiModelProperty(position = 3)
    private Double amountDue;

}