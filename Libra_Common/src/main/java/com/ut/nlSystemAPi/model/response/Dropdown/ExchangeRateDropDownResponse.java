package com.ut.nlSystemAPi.model.response.Dropdown;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExchangeRateDropDownResponse {

    @ApiModelProperty(position = 1)
    private Long currencyCenterId;

    @ApiModelProperty(position = 2)
    private Long exchangeRateId;

    @ApiModelProperty(position = 3)
    private String name;

    @ApiModelProperty(position = 4)
    private String symbol;

    @ApiModelProperty(position = 5)
    private Double rateToSell;

    @ApiModelProperty(position = 6)
    private Double rateToPurchase;
}
