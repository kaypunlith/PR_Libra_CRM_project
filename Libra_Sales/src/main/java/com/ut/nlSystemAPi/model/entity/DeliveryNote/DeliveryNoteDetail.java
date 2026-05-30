package com.ut.nlSystemAPi.model.entity.DeliveryNote;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryNoteDetail extends BaseModel {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long deliveryId;

  @ApiModelProperty(position = 3)
  private Long salesInvoiceId;

  @ApiModelProperty(position = 4)
  private Long salesInvoiceDetailId;

  @ApiModelProperty(position = 5)
  private Long productId;

  @ApiModelProperty(position = 6)
  private Long locationId;

  @ApiModelProperty(position = 7)
  private String lotsNumber;

  @ApiModelProperty(position = 8)
  private Long qty;

  @ApiModelProperty(position = 9)
  private String expiredDate;

}