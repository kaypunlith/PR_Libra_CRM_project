package com.ut.nlSystemAPi.model.request.Login.StockLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockLevelRequest {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private Long priceTypeId;
}
