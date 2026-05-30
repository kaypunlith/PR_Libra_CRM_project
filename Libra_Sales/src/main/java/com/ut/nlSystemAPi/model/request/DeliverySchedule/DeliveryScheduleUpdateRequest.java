package com.ut.nlSystemAPi.model.request.DeliverySchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryScheduleUpdateRequest extends DeliveryScheduleRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 10)
    private String comment;

}
