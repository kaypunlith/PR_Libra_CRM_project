package com.ut.nlSystemAPi.model.response.DeliverySchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryScheduleDetailResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long totalOrder;

    @ApiModelProperty(position = 2)
    private Long saleOrderId;

    @ApiModelProperty(position = 3)
    private String saleOrderCode;

    @ApiModelProperty(position = 4)
    private Double amountDue;

    @ApiModelProperty(position = 4)
    private Double totalAmount;

    @ApiModelProperty(position = 5)
    private String customerName;

}
