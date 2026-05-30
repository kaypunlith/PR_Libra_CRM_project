package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Filter extends FilterBase {

  @ApiModelProperty(position = 3, hidden = true)
  private Long moduleId;

  @ApiModelProperty(position = 4, hidden = true)
  private String orderBy;

  @ApiModelProperty(position = 5)
  private String searchText;

  // Optional selector for chart-account-balance/list
  // 1 = Retained Earnings, 2 = Income Summary; null/others = normal
  @ApiModelProperty(position = 6)
  private Long type;

  @ApiModelProperty(position = 7, hidden = true)
  private Long viewByUser = 0L;
}

