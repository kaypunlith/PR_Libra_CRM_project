package com.ut.nlSystemAPi.model.request;

import java.util.List;

import com.ut.nlSystemAPi.model.base.BaseModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MissionRequest extends BaseModel {

  @ApiModelProperty(position = 1)
  private String startDate;

  @ApiModelProperty(position = 1)
  private String endDate;

  @ApiModelProperty(position = 1)
  private String description;

  @ApiModelProperty(position = 1)
  private List<Long> means;

  @ApiModelProperty(position = 1)
  private List<Long> organizations;

  @ApiModelProperty(position = 1)
  private List<Long> destinations;

}
