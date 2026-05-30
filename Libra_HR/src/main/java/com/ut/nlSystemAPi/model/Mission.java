package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Mission extends BaseModel {

  @ApiModelProperty(position = 1)
  private Long workingTimeId;

  @ApiModelProperty(position = 2)
  private Long id;

  @ApiModelProperty(position = 1)
  private String startDate;

  @ApiModelProperty(position = 1)
  private String endDate;

  @ApiModelProperty(position = 1)
  private String description;
}
