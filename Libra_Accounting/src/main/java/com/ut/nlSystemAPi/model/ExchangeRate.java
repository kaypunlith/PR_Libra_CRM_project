package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExchangeRate extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long companyId;

  @ApiModelProperty(position = 2)
  private Long exchangeRateId;

  @ApiModelProperty(position = 3)
  private String companyName;

  @ApiModelProperty(position = 4)
  private String currencyFrom;

  @ApiModelProperty(position = 5)
  private Double rate;

  @ApiModelProperty(position = 6)
  private Long currencyCenterId;

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

}
