package com.ut.nlSystemAPi.model.request.Login.PriceRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendToTelegram {
    @ApiModelProperty(position = 1)
    private Long id;
}
