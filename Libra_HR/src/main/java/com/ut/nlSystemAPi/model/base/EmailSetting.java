package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmailSetting {

  @ApiModelProperty(position = 1)
  private String host;

  @ApiModelProperty(position = 2)
  private int port;

  @ApiModelProperty(position = 3)
  private String username;

  @ApiModelProperty(position = 4)
  private String password;

}