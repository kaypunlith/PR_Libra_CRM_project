package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseFile {

  @ApiModelProperty(position = 1)
  private String url;

  @ApiModelProperty(position = 2)
  private String name;

}
