package com.ut.nlSystemAPi.model.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ViewOtFilter {
    @ApiModelProperty(position = 1)
    private Long employeeId;

    @ApiModelProperty(position = 2)
    private String date;
}
