package com.ut.nlSystemAPi.model.request.Login.TransferSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferScheduleDetailRequest {

    @ApiModelProperty(position = 2)
    private Long pvRequestId;
}
