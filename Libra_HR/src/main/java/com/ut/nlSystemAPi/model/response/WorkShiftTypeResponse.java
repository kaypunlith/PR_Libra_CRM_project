package com.ut.nlSystemAPi.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WorkShiftTypeResponse {
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 10)
    private String name;


}
