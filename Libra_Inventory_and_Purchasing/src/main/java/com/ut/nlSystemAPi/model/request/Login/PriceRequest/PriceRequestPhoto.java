package com.ut.nlSystemAPi.model.request.Login.PriceRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceRequestPhoto {

    @ApiModelProperty(position = 1)
    private String url;

    @ApiModelProperty(position = 2)
    private String name;
}
