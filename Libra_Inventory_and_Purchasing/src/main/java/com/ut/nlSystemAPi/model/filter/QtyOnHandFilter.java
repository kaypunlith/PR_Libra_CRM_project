package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QtyOnHandFilter {

    @ApiModelProperty(position = 10)
    private Long locationId;

    @ApiModelProperty(position = 10)
    private String date;

    @ApiModelProperty(position = 10)
    private Long isExpired;

    @ApiModelProperty(position = 10)
    private Long productId;

}
