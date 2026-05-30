package com.ut.nlSystemAPi.model.request.Zone;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZoneRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String lats;

    @ApiModelProperty(position = 3)
    private String longs;

    @ApiModelProperty(position = 4)
    private String radius;

    @ApiModelProperty(position = 5)
    private List<ZoneDetailRequest> paths;
}
