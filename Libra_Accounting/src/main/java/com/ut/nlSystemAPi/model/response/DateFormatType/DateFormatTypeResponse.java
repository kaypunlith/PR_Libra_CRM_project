package com.ut.nlSystemAPi.model.response.DateFormatType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DateFormatTypeResponse {
    @ApiModelProperty(position = 1)
    private Long dateFormatId;

    @ApiModelProperty(position = 3)
    private Long timeFormatId;


}
