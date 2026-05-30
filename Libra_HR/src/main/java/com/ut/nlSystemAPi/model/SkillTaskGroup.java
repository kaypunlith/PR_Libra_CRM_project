package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SkillTaskGroup extends BaseModel {

  @ApiModelProperty(position = 0)
  private Long id;

  @ApiModelProperty(position = 1)
  private String name;

  @ApiModelProperty(position = 2)
  private Double weight;

}
