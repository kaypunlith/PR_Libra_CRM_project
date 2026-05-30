package com.ut.nlSystemAPi.model.response.Zone;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ZoneDetailResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String lats;

    @ApiModelProperty(position = 3)
    private String longs;
}
