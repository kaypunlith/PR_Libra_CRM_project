package com.ut.nlSystemAPi.model.request.Market;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarketUpdateRequest extends MarketRequest {

    @ApiModelProperty(position = 0)
    private Long id;
}
