package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtherPayReportResponse {
  @ApiModelProperty(position = 10)
    private Long id;

    @ApiModelProperty(position = 42)
    private String name;

    @ApiModelProperty(position = 43)
    private Float amount;
}
