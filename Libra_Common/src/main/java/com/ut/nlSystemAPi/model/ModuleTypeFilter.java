package com.ut.nlSystemAPi.model;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModuleTypeFilter extends Filter {

  @ApiModelProperty(position = 101)
  private Long roleId;

}
