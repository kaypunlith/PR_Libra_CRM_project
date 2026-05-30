package com.ut.nlSystemAPi.model.response.ARSchedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class ARScheduleResponseDetails {

    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private Double amountDue;

    @ApiModelProperty(position = 4)
    private String invoice;

    @ApiModelProperty(position = 2)
    private String customerName;
}
