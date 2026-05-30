package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StockOrder extends BaseModel {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long saleInvoiceId;

  @ApiModelProperty(position = 3)
  private Long productId;

  @ApiModelProperty(position = 4)
  private Long locationGroupId;

  @ApiModelProperty(position = 5)
  private Long locationId;

  @ApiModelProperty(position = 6)
  private String lotsNumber;

  @ApiModelProperty(position = 7)
  private String expiredDate;

  @ApiModelProperty(position = 8)
  private String date;

  @ApiModelProperty(position = 9)
  private Long qty;
}