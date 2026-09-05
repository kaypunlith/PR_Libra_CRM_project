package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DropdownResponse {

  @ApiModelProperty(position = 1)
  private Long id;

  @ApiModelProperty(position = 2)
  private String name;

  @ApiModelProperty(position = 1)
  private Long contactId;

  @ApiModelProperty(position = 2)
  private String contactName;

  @ApiModelProperty(position = 3)
  private String code;

  @ApiModelProperty(position = 4)
  private String photo;
}
