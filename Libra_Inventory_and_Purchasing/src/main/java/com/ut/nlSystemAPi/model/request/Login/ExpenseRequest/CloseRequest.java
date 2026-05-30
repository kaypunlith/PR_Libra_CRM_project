package com.ut.nlSystemAPi.model.request.Login.ExpenseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CloseRequest  {

      @ApiModelProperty(position = 1)
      private Long id;

      @ApiModelProperty(position = 3,hidden = true)
      private Long closedBy;

      @ApiModelProperty(position = 4,hidden = true)
      private String closed;

}
