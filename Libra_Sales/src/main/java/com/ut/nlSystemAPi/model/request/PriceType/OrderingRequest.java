package com.ut.nlSystemAPi.model.request.PriceType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderingRequest {

    @ApiModelProperty(position = 2)
    private Long id;

    @ApiModelProperty(position = 3)
    private Long ordering;

}
