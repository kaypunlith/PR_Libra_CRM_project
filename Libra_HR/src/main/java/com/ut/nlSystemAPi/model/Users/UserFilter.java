package com.ut.nlSystemAPi.model.Users;

import com.ut.nlSystemAPi.model.base.Filter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserFilter extends Filter {

  @ApiModelProperty(position = 101)
  private Long groupId;

}
