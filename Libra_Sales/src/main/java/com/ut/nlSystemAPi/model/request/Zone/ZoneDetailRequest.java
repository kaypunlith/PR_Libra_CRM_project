package com.ut.nlSystemAPi.model.request.Zone;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ZoneDetailRequest {

    @ApiModelProperty(position = 1)
    private String lats;

    @ApiModelProperty(position = 2)
    private String longs;
}
