package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.Users.UserList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Notification {

  @ApiModelProperty(position = 1)
  private String message;

  @ApiModelProperty(position = 2)
  private String content;

  @ApiModelProperty(position = 3)
  private List<String> deviceTokens;

}
