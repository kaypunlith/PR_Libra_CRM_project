package com.ut.nlSystemAPi.model.entity.DeliveryNote;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeliveryNote extends BaseModel {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long companyId;

  @ApiModelProperty(position = 3)
  private String date;

  @ApiModelProperty(position = 4)
  private Long customerContactId;

  @ApiModelProperty(position = 5)
  private String shipTo;

  @ApiModelProperty(position = 6)
  private String note;

}