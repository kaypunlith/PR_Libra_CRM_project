package com.ut.nlSystemAPi.model.response.Inspection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuarterResponse {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String startDate;

    @ApiModelProperty(position = 4)
    private String toDate;

}
