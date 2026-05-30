package com.ut.nlSystemAPi.model.request.Login.PriceRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceRequestStatusUpdate {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long status;

    @ApiModelProperty(position = 2)
    private Long closeReasonId;

    @ApiModelProperty(position = 3, hidden = true)
    private Long closeBy;

    @ApiModelProperty(position = 4, hidden = true)
    private Long convertBy;
}
