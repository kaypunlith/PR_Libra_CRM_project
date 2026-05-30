package com.ut.nlSystemAPi.model.request.Login.ARAPSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ARAPScheduleRequestDetails {
    @ApiModelProperty(position = 5)
    private Long invoiceId;
    @ApiModelProperty(position = 5)
    private Double amountDue;
}
