package com.ut.nlSystemAPi.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MissionUpdateRequest extends MissionRequest {

  @ApiModelProperty(position = 1)
  private Long id;

}
