package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionUpdateStatusRequest {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private int status;
}
