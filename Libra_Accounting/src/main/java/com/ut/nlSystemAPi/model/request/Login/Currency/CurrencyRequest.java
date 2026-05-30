package com.ut.nlSystemAPi.model.request.Login.Currency;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CurrencyRequest {

    @ApiModelProperty(position = 4)
    private String name;

    @ApiModelProperty(position = 11)
    private String symbol;

}
