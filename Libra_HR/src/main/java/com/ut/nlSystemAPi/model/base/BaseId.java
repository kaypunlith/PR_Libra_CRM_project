package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseId {

  @ApiModelProperty(position = 1)
  private Long id;

}
