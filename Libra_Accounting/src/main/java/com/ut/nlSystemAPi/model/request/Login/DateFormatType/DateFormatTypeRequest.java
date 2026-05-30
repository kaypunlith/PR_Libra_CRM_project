package com.ut.nlSystemAPi.model.request.Login.DateFormatType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DateFormatTypeRequest {
    @ApiModelProperty(position = 4)
    private Long DateFormatId;

    @ApiModelProperty(position = 5)
    private Long TimeFormatId;

}
