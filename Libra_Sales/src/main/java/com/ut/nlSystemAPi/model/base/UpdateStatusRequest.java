package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateStatusRequest {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private Long status;

}
