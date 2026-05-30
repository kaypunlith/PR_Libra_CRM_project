package com.ut.nlSystemAPi.model.request.Zone;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ZoneUpdateRequest extends ZoneRequest {

    @ApiModelProperty(position = 0)
    private Long id;
}
