package com.ut.nlSystemAPi.model.response.APSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class APScheduleResponseDetails {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 4)
    private Double amountDue;

    @ApiModelProperty(position = 5)
    private String name;

    @ApiModelProperty(position = 5)
    private String vendorName;

    @ApiModelProperty(position = 5)
    private String backgroundColor;
}
