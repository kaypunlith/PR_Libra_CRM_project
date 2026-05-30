package com.ut.nlSystemAPi.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Filter {

  @ApiModelProperty(position = 1)
  private int page;

  @ApiModelProperty(position = 2)
  private int rowsPerPage;

  @ApiModelProperty(position = 3)
  private String searchText;

  @ApiModelProperty(position = 4, hidden = true)
  private String orderBy; 

}
