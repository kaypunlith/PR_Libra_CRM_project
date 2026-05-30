package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LocationPointRequest {

    @ApiModelProperty(position = 10)
    private String lats;

    @ApiModelProperty(position = 20)
    private String longs;

}
