package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FilterBase {

  @ApiModelProperty(position = 1)
  private Integer page;

  @ApiModelProperty(position = 2)
  private Integer rowsPerPage;
}
