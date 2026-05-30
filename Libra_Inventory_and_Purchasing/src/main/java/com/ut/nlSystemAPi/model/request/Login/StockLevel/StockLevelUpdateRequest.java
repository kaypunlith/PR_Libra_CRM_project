package com.ut.nlSystemAPi.model.request.Login.StockLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockLevelUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Long priceTypeId;
}
