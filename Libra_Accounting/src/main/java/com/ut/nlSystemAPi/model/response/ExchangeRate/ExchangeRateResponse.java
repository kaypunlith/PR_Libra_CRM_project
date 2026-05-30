package com.ut.nlSystemAPi.model.response.ExchangeRate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExchangeRateResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private Long companyId;

    @ApiModelProperty(position = 3)
    private String companyName;

    @ApiModelProperty(position = 4)
    private String currencyFrom;

    @ApiModelProperty(position = 5)
    private Double rate;

    @ApiModelProperty(position = 6)
    private Long currencyToId;

    @ApiModelProperty(position = 7)
    private String currencyTo;

    @ApiModelProperty(position = 8)
    private Long isPosDefault;

    @ApiModelProperty(position = 9)
    private Double rateForSell;

    @ApiModelProperty(position = 10)
    private Double rateForChange;

    @ApiModelProperty(position = 12)
    private Double rateForPurchase;

    @ApiModelProperty(position = 21)
    private String createdDate;

    @ApiModelProperty(position = 22)
    private String modifiedDate;

    @ApiModelProperty(position = 23)
    private String createdBy;

    @ApiModelProperty(position = 24)
    private String modifiedBy;

    @ApiModelProperty(position = 25)
    private Long status;

}
