package com.ut.nlSystemAPi.model.response.TransferScheduleResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferScheduleDetailResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long pvRequestId;

    @ApiModelProperty(position = 3)
    private String pvRequestNumber;

    @ApiModelProperty(position = 4)
    private Double totalAmount;

}
