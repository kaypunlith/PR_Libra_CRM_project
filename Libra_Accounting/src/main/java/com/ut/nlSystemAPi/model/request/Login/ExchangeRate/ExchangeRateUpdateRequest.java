package com.ut.nlSystemAPi.model.request.Login.ExchangeRate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExchangeRateUpdateRequest {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 4)
    private Double rateForSell;

    @ApiModelProperty(position = 5)
    private Double rateForChange;

    @ApiModelProperty(position = 11)
    private Double rateForPurchase;

}
