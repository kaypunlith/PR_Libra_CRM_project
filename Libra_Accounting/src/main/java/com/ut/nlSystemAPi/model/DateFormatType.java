package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public class DateFormatType {
  @ApiModelProperty(position = 4)
  private Long DateFormatId;

  @ApiModelProperty(position = 5)
  private Long TimeFormatId;

  @ApiModelProperty(position = 11)
  private Long isUsed;
}
