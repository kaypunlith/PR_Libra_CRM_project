package com.ut.nlSystemAPi.model.request.Login.Taxation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaxationExchangeRateRequest {

    @ApiModelProperty(position = 1)
    private Double exchangeRateAmount;

}
