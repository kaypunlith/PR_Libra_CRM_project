package com.ut.nlSystemAPi.model.filter;

import com.ut.nlSystemAPi.model.base.Filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MissionFilter extends Filter {
  @ApiModelProperty(position = 1)
  private String startDate;

  @ApiModelProperty(position = 2)
  private String endDate;

  @ApiModelProperty(position = 3)
  private String searchText;

  @ApiModelProperty(position = 4)
  private Integer status;
}
