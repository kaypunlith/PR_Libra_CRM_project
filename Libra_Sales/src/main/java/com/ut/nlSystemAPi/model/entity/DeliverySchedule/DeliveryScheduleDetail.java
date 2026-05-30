package com.ut.nlSystemAPi.model.entity.DeliverySchedule;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryScheduleDetail extends BaseModel {

    @ApiModelProperty(position = 1)
    private Long deliveryScheduleId;

    @ApiModelProperty(position = 2)
    private Long saleOrderId;

    @ApiModelProperty(position = 3)
    private Double amountDue;

}
