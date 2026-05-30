package com.ut.nlSystemAPi.model.request.Market;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MarketRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Long zoneId;
}
